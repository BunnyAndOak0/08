package edu.kmust.bully.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.kmust.bully.common.exception.InsertException;
import edu.kmust.bully.common.exception.ModelNotFoundException;
import edu.kmust.bully.common.exception.PhoneDuplicateException;
import edu.kmust.bully.common.exception.UserNameException;
import edu.kmust.bully.common.model.User;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.user.mapper.UserMapper;
import edu.kmust.bully.user.service.IUserService;
import edu.kmust.bully.user.util.EncodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用于注册登录的基本信息的存储 服务实现类
 * </p>
 *
 * @author BunyAndOak
 * @since 2020-08-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    StringRedisTemplate redisTemplate;

    public static final String PREFIX_INFO = "USER_CODE_INFO:";

    public void userRegister(User user){
        QueryWrapper<User> userQueryWrapper1 = new QueryWrapper<>();
        userQueryWrapper1.eq("username", user.getUsername());
        //查询该用户用是否已经被使用
        User u1 = userMapper.selectOne(userQueryWrapper1);
        if (u1 != null){
            //不为空说明该用户名已经被占用
            throw new UserNameException("该用户名已被使用！");
        }

        QueryWrapper<User> userQueryWrapper2 = new QueryWrapper<>();
        userQueryWrapper2.eq("phone", user.getPhone());
        User u2 = userMapper.selectOne(userQueryWrapper2);
        if (u2 != null){
            //该手机号不为空 说明已经被占用
            throw new PhoneDuplicateException("该手机号已被注册！");
        }

        //将密码进行加密
        String password = EncodeUtils.encode(user.getPassword());
        user.setPassword(password);

        int count = userMapper.insert(user);
        //插入失败！
        if (count != 1){
            throw new InsertException("服务器繁忙，注册失败，请稍后再试");
        }
    }

    /*
     * @Author BunnyAndOak0
     * @Description 发送一个验证码
     **/
    public void sendCode(String phone){
        if (StringUtils.isEmpty(phone)){
            return;
        }
        String code = String.valueOf((int)(Math.random() * 1000000));
        System.out.println(code);
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);

        rabbitTemplate.convertAndSend("BULLY.SMS.EXCHANGE",
                "bully.sendMessage", map);
        System.out.println("已添加到队列");
        //使用redis将生成的验证码存入到缓存中 并 设置五分钟过期
        redisTemplate.opsForValue().set(PREFIX_INFO + phone, code, 5,
                TimeUnit.MINUTES);
    }

    /*
     * @Author BunnyAndOak0
     * @Description 根据用户名获取手机 以便进行验证码的发送
     **/
    public String getPhone(String username){
        String phone = userMapper.selectByName(username);
        if (phone == null){
            throw new ModelNotFoundException("该用户没有注册过");
        }
        return phone;
    }

    /*
     * @Author BunnyAndOak0
     * @Description 进行密码修改
     **/
    public void updateByPhone(String phone, String password){
        String epassword = EncodeUtils.encode(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Integer count = userMapper.selectCount(queryWrapper);
        if (count == 0){
            throw new ModelNotFoundException("该用户不存在!");
        }
        int num = userMapper.updateByPhone(phone, epassword);
        if (num == 0){
            return ;
        }
    }

    /*
     * @Author BunnyAndOak0
     * @Description举报 report 每个人的初始值为0 每次被举报就就+1 为3时被禁言
     **/
    public int userReport(String username){
        int count = userMapper.forbiddenUser(username);
        return count;
    }

    /*
     * @Author BunnyAndOak0
     * @Description 禁言重置
     **/
    public void reportReset(String username){
        userMapper.reportReset(username);
    }

    /*
     * @Author BunnyAndOak0
     * @Description 查询禁言状态
     **/
    public int getReport(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user.getForbidden();
    }

    /*
     * @Author BunnyAndOak0
     * @Description 编辑修改个人信息
     * @RequestParam("username") String username,
       @RequestParam("birth") String birth,
       @RequestParam("sex") int sex,
       @RequestParam("location") String location
     **/
    public int updateUser(String oldName, String newName, LocalDateTime birth,
                           int sexNum, String location){
        return userMapper.updateUser(oldName, newName, birth, sexNum, location);
    }

    public User getUserById(Integer id){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

}
