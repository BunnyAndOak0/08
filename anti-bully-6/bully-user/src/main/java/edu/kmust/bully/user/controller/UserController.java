package edu.kmust.bully.user.controller;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import edu.kmust.bully.common.exception.ParameterValidationException;
import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.model.User;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import edu.kmust.bully.publish.api.PublicModelApi;
import edu.kmust.bully.user.dto.UserPlus;
import edu.kmust.bully.user.feign.PrivateModelClient;
import edu.kmust.bully.user.feign.PublicModelClient;
import edu.kmust.bully.user.service.ISentenceService;
import edu.kmust.bully.user.service.IUserService;
import edu.kmust.bully.user.util.RedisForbbidenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static edu.kmust.bully.user.service.impl.UserServiceImpl.PREFIX_INFO;

/**
 * <p>
 * 用于注册登录的基本信息的存储 前端控制器
 * </p>
 *
 * @author BunyAndOak
 * @since 2020-08-14
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    ISentenceService sentenceService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Value("${bully.SessionName}")
    private String sessionName;
    private static final String FORBIDDEN_USER = "FORBIDDEN_USER:";
    private static final String USER_INFO = "USER_INFO:";

    //http://localhost:10000/user/register?username=alice&password=123&role=1
    @PostMapping("/register")
    public ModelAndView userRegister(@Validated User user,
                                     @RequestParam String code,
                                     BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("validated的错误信息为：{}",
                    bindingResult.getFieldError().getDefaultMessage());
            throw new ParameterValidationException();
        }

        String checkCode = redisTemplate.opsForValue().get(PREFIX_INFO + user.getPhone());

        if (!(checkCode.equals(code))){
//            return R.failure(4001, "验证码错误！");
            return new ModelAndView("success");
        }

        userService.userRegister(user);
//        return R.ok();
        return new ModelAndView("index");
    }

    @PostMapping("/sendCode")
    public R sendCode(@RequestParam("phone") String phone){
        if (!phone.matches("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$")) {
            return R.failure(R.State.ERR_PHONE_DUPLICATE, "该手机号不合法！");
        }
        userService.sendCode(phone);
        return R.ok();
    }

    @PostMapping("/forget/sendCode")
    public R sendForgetCode(@RequestParam("phone") String phone){
        userService.sendCode(phone);
        return R.ok("发送验证码成功！");
    }

    /*
     * @Author BunnyAndOak0
     * @Description 忘记密码后进行密码修改
     **/
    @PostMapping("/forget/password")
    public Object forgetPassword(@RequestParam("phone") String phone,
                            @RequestParam("password") String password,
                            @RequestParam("code") String code){
        if (phone == null || password == null || code == null ){
            return R.failure(R.State.ERR_RIGESTER, "数据不可为空");
        }
        String checkCode = redisTemplate.opsForValue().get(PREFIX_INFO +
                phone);
        if (checkCode == null){
            return R.failure(R.State.ERR_UPDATE, "验证码已经失效！");
        }else if (!checkCode.equals(code)){
            return R.failure(4001, "验证码错误");
        }
            //密码修改成功后删除验证码！
            userService.updateByPhone(phone, password);
            redisTemplate.delete(PREFIX_INFO + phone);
        return R.ok("密码修改成功！");
    }

    /*
     * @Author BunnyAndOak0
     * @Description 个人头像 用户名 展示 以及每日一句展示
     * 作为用户编辑界面的时候的数据回显
     **/
    @GetMapping("/getUser")
    public R getUser(@AuthenticationPrincipal UserInfo user){
        if (user == null){
            return R.failure(R.State.ERR_RIGESTER, "该用户未登录");
        }

        String userJson = redisTemplate.opsForValue().get(USER_INFO + user.getUsername());
        UserPlus userPlus = new UserPlus();
        String sentence;
        Gson gson = new Gson();

        //缓存中没有的话 就加入
        if (userJson == null){
            sentence = sentenceService.getSentence();

            userPlus.setId(user.getId());
            userPlus.setUsername(user.getUsername());
            userPlus.setPhone(user.getPhone());
            userPlus.setRole(user.getRole());
            userPlus.setPortrait(user.getPortrait());

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime time = user.getBirth();
            String birthday = df.format(time);

            userPlus.setBirthday(birthday);
            userPlus.setSex(user.getSex());
            userPlus.setLocation(user.getLocation());

            userPlus.setSentence(sentence);

            userJson = gson.toJson(userPlus);

            redisTemplate.opsForValue().set(USER_INFO + user.getUsername(),
                    userJson, 3, TimeUnit.HOURS);
        }else {
            //缓存中有的话 就从缓存中直接获取
            userPlus = gson.fromJson(userJson, UserPlus.class);
        }
        System.out.println(userPlus);
        return R.ok("用户信息为：", userPlus);
    }

     /*
     * @Author BunnyAndOak0
     * @Description 远程调用model微服务 并通过共享session获取当前登录的用户id
     *              获取发布过的信息
     * 公开和匿名分类
     **/
    @Autowired
    PublicModelClient publicModelClient;
    @Autowired
    PrivateModelClient privateModelClient;

    @GetMapping("/my/public")
    public R getPublish(@RequestParam("page") Integer page,
                        @AuthenticationPrincipal UserInfo user){
        Long userId = user.getId();
        List<Model> publicModels = publicModelClient.publicModel(page, userId);
        if (publicModels == null){
            return R.failure("还没有发布过公开动态！");
        }
        System.out.println(publicModels);
        return R.ok("该用户发布的公开动态为：", publicModels);
    }
    //获取个人私有的动态
    @GetMapping("/my/private")
    public R getPrivate(@RequestParam("page") Integer page,
                        @AuthenticationPrincipal UserInfo user){
        List<Model> privateModels = privateModelClient.privateModel(page, user.getId());
        if (privateModels == null){
            return R.failure("还没有发布过匿名动态！");
        }
        return R.ok("该用户发布的匿名动态为：", privateModels);
    }

    /*
     * @Author BunnyAndOak0
     * @Description 对用户进行举报 初始都是0 每次被举报+1 为3的时候被禁言
     *  存入redis中并设置3天后删除缓存 也就是解除禁言
     * todo 发布和评论之前也需要先查找该用户是否被禁言
     **/
    @Autowired
    RedisForbbidenUtils redisForbbidenUtils;

    @GetMapping("/report")
    public R userReport(@RequestParam("username") String username){
        //先在redis缓存中查看是否有该用户 不存在的话说明该用户还没有被禁言 可以进行更新操作
//        String forbidden = redisTemplate.opsForValue().get(FORBIDDEN_USER + username);
        if (redisForbbidenUtils.forbidden(username)){
            //缓存中没有查到
            int i = userService.getReport(username);
            System.out.println("被举报：" + i);
            if (i == 3){
                redisTemplate.opsForValue().set(FORBIDDEN_USER + username, username + "被禁言",
                        3, TimeUnit.DAYS);
                //重置禁言
                userService.reportReset(username);
                return R.failure(R.State.ERR_UPDATE, "该用户已被禁言！");
            }else{
                //进行举报
                userService.userReport(username);
                return R.ok("已举报成功！");
            }
        }else{
            return R.failure(R.State.ERR_UPDATE, "该用户已被禁言！");
        }
    }

    /*
     * @Author BunnyAndOak0
     * @Description 用户主动修改密码时发送验证码
     **/
    @PostMapping("/update/sendCode")
    public void sendUpdateCode(@AuthenticationPrincipal UserInfo user){
        String phone = userService.getPhone(user.getUsername());
        userService.sendCode(phone);
    }

    /*
     * @Author BunnyAndOak0
     * @Description 用户主动修改密码
     * 有了界面之后改成post方式提交 页面重定向
     **/
    @PostMapping("/update/password")
    public Object updatePassword(@AuthenticationPrincipal UserInfo user,
                                 @RequestParam("code") String code,
                                 @RequestParam("password") String password,
                                 HttpServletResponse response) throws IOException {
        String phone = user.getPhone();
        String checkCode = redisTemplate.opsForValue().get(PREFIX_INFO +
                phone);
        if (!checkCode.equals(code)){
            return R.failure(4001, "验证码错误");
        }

        userService.updateByPhone(phone, password);
        //删除redis里面的验证码
        redisTemplate.delete(PREFIX_INFO + phone);
//        return new ModelAndView("success");
        return R.ok("密码修改成功！");
    }

    /*
     * @Author BunnyAndOak0
     * @Description 个人信息的修改
     * usernamevarchar(20) NOT NULL注册的用户名，不可以重复
       passwordvarchar(100) NOT NULL登陆密码
       phonevarchar(20) NOT NULL用于实名验证或者忘记密码时的验证
       portraitvarchar(500) NULL存储头像的图片地址，可以为空
       forbiddentinyint(4) NOT NULL用于查看该用户是否被禁言，举报三次被禁言，每次举报加一，为三时不能再发表评论和动态，初始都为0
       roletinyint(4) NOT NULL角色分为0，1，2，三档，0为普通用户，1为辅导专区人员，2为官方帐户，注册的时候做区分
       birthdatetime NULL不可为空 默认为创建时间
       sex tinyint(4) NOT NULL不可为空 1为男 0为女 未知为2
       locationvarchar(50) NOT NULL
       * todo 编辑用户信息的时候 需要传过来的日期是哪种类型
     **/
    @PostMapping("/update/user")
    public R updateUser(@AuthenticationPrincipal UserInfo user,
                       @RequestParam("username") String username,
                       @RequestParam("birthday") String birth,
                       @RequestParam("sex") int sex,
                       @RequestParam("location") String location){

        String oldName = user.getUsername();
        LocalDate localDate = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime birthday = localDate.atStartOfDay();
        birthday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int result = userService.updateUser(oldName, username,
                birthday, sex, location);

        if (result == 0){
            return R.failure(R.State.ERR_UPDATE, "更新失败");
        }else {
            //修改后存入缓存中
            Gson gson = new Gson();

            UserPlus userPlus = gson.fromJson(redisTemplate.opsForValue().get(USER_INFO + username), UserPlus.class);

            userPlus.setUsername(username);
            userPlus.setBirthday(birth);
            userPlus.setSex(sex);
            userPlus.setLocation(location);
            String uesrJson = gson.toJson(userPlus);
            System.out.println(userPlus);
            redisTemplate.opsForValue().set(USER_INFO + user.getUsername(), uesrJson);
        }
        return R.ok("用户修改成功！");
    }

    //点击头像时返回用户信息 包括头像 名字 发布过的公开动态等等
    //我也不知道用不用的上
    @GetMapping("/getUserById")
    public R getUserById(@RequestParam("id")Integer id){
//        R<Map<String, Object>> publicModels = publicModelClient.getPublicById(id);
//        Map<String, Object> modelMap = publicModels.getData();
        User user = userService.getUserById(id);
        if (user == null){
            return R.failure(R.State.ERR_MODEL_NOT_FOUND, "该用户不存在");
        }
        Map<String, Object> userById = new HashMap<>();
        userById.put("user", user);
        userById.put("modelMap", publicModelClient.getPublicById(id).getData());
        return R.ok("根据id查询的用户信息为：", userById);
    }
}

