package edu.kmust.bully.publish.controller;

import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.FileEmptyException;
import edu.kmust.bully.common.exception.FileIOException;
import edu.kmust.bully.common.exception.FileSizeException;
import edu.kmust.bully.common.exception.FileTypeException;
import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import edu.kmust.bully.common.dto.ModelDTO;
import edu.kmust.bully.publish.service.IModelService;
import edu.kmust.bully.common.vo.ModelListItemVO;
import edu.kmust.bully.publish.utils.RedisForbbidenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 公开动态的数据存储 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */
@Slf4j
@RestController
@RequestMapping("/publish/models")
public class ModelController {
    @Value("${project.model.image-upload-path}")
    private String imageUploadPath;
    @Value(("${project.model.image-host}"))
    private String imageHost;
    @Value("${project.model.image-max-size}")
    private long imageMaxSize;
    @Value(("${project.model.image-content-types}"))
    private List<String> imageContentTypes;

    @Autowired
    RedisForbbidenUtils redisForbbidenUtils;

    @PostMapping("/upload-image")
    public R<String> uploadImage(MultipartFile imageFile) {
        // 判断上传的文件是否为空
        if (imageFile.isEmpty()) {
            throw new FileEmptyException("上传图片失败！请选择有效的图片文件！");
        }
        // 判断上传的文件大小是否超标
        if (imageFile.getSize() > imageMaxSize) {
            throw new FileSizeException("上传图片失败！不允许使用超过" + (imageMaxSize / 1024) + "KB的图片文件！");
        }
        // 判断上传的文件类型是否超标
        if (!imageContentTypes.contains(imageFile.getContentType())) {
            throw new FileTypeException("上传图片失败！图片类型错误！允许上传的图片类型有：" + imageContentTypes);
        }

        // 确定本次上传时使用的文件夹
        String dir = DateTimeFormatter.ofPattern("yyyy/MM").format(LocalDateTime.now());
        File parent = new File(imageUploadPath, dir);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        log.debug("dir >>> {}", parent);

        // 确定本次上传时使用的文件名
        String filename = UUID.randomUUID().toString();
        String originalFilename = imageFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String child = filename + suffix;

        // 创建最终保存时的文件对象
        File dest = new File(parent, child);

        // 执行保存
        try {
            imageFile.transferTo(dest);
        } catch (IOException e) {
            throw new FileIOException("上传图片失败！当前服务器忙，请稍后再次尝试！");
        }

        // 确定网络访问路径
        String imageUrl = imageHost + dir + "/" + child; // http://localhost:8081/1.jpg
        log.debug("image url >>> {}", imageUrl);

        // 返回
        return R.ok(imageUrl);
    }

    /**
     * 公开发布
     */
    @Autowired
    private IModelService modelService;
    //http://localhost:8080/api/v1/models/public/send?title=qilin&content=zenmeban
    @RequestMapping("/public/send")
    public R publicSend(@RequestBody ModelDTO modelDTO, @AuthenticationPrincipal UserInfo userInfo){
        if (redisForbbidenUtils.forbidden(userInfo.getUsername())){
            modelService.publicModel(modelDTO,userInfo.getId(),userInfo.getUsername());
            return R.ok();
        }
        return R.failure(R.State.ERR_INSERT, "该用户已经被禁言");
    }

    /**
     * 匿名发布
     * @param modelDTO
     * @param userInfo
     * @return
     */
    //http://localhost:8080/publish/models/private/send?title=balin&content=xunqiubangzhu
    @RequestMapping("/private/send")
    public R PrivateSend(@RequestBody ModelDTO modelDTO,
                         @AuthenticationPrincipal UserInfo userInfo){
        if (redisForbbidenUtils.forbidden(userInfo.getUsername())){
            modelService.privateModel(modelDTO,userInfo.getId());
            return R.ok();
        }
        return R.failure(R.State.ERR_INSERT, "该用户已经被禁言");
    }

    @GetMapping("/likes")
    public R<List<ModelListItemVO>> mostLikes(){
        return R.ok(modelService.getMostLikes());
    }

    //心灵辅导区的发布
    //http://localhost:8080/publish/models/soul/send?title=balin&content=xunqiubangzhu
    @RequestMapping("/soul/send")
    public R soulSend(@RequestBody ModelDTO modelDTO, @AuthenticationPrincipal UserInfo userInfo){
        if (redisForbbidenUtils.forbidden(userInfo.getUsername())){
            modelService.soulModel(modelDTO,userInfo.getId(),userInfo.getUsername(),userInfo.getRole());
            return R.ok();
        }
        return R.failure(R.State.ERR_INSERT, "该用户已经被禁言");
    }

    /**
     * 主页非匿名
     * @param page
     * @param userInfo
     * @return
     */
    //根据id查询当前用户发布过的公开动态
    @GetMapping("/index/model")
    public R<List<Model>> NotIsAnonymousnessListModels(@RequestParam("page") Integer page,
                       @AuthenticationPrincipal UserInfo userInfo){
        if (page == null || page < 1) {
            page = 1;
        }
        return R.ok(modelService.getListByNotIsAnonymousness(userInfo.getId(),page));
    }

    //这个主要是为了给其他服务调用写的 个人公开模块 用openfeign进行调用 返回一个list
    @GetMapping("/public/my")
    public List<Model> publicModel(Integer page,
                                   @RequestParam("userId")Long userId){
        if (page == null || page < 1) {
            page = 1;
        }
        PageInfo<Model> publicModels = modelService.getListByNotIsAnonymousness
                (userId, page);
        return publicModels.getList();
    }

    //todo 所有公开动态应该可以加入缓存
    //查询所有的公开动态并且显示在首页 是所有所有所有公开动态 重要的事情说三遍
    @GetMapping("/public/all")
    public R<Map<String, Object>> publicAllModel(@RequestParam("page") Integer page){
        if (page == null || page < 0){
            page =1;
        }
        PageInfo<Model> publicAll = modelService.getPublicAll(page);
        Map<String, Object> map = new HashMap<>();
        map.put("publicAll", publicAll);
        return R.ok("获取到的所有公开的动态为：", map);
    }

    //openFeign 给es创建索引的时候用
    @GetMapping("/public/feignAll")
    public List<Model> publicFeignAllModel(@RequestParam("page") Integer page){
        if (page == null || page < 0){
            page =1;
        }
        PageInfo<Model> publicAll = modelService.getPublicAll(page);
        return publicAll.getList();
    }

    //根据用户id获取动态 就是展示别人发布过的公开动态 用于显示别人的所有个人信息 包括用户名 头像 发布过的动态等等
    @RequestMapping("/getPublicById/{id}")
    public R<Map<String, Object>> getPublicById(@PathVariable("id") Integer id){
        List<Model> publicById = modelService.getPublicById(id);
        Map<String, Object> publicMap = new HashMap<>();
        publicMap.put("publicMap", publicById);
        return R.ok("根据id获取到的用户动态为：", publicMap);
    }

    /**
     * 树洞匿名
     * @param page
     * @param userInfo
     * @return
     */
    //根据id查询当前登录用户发布过的匿名动态
    @GetMapping("/shudong/model")
    public R<List<Model>> IsAnonymousnessListModels(@RequestParam("page") Integer page,
                                                    @AuthenticationPrincipal UserInfo userInfo){
        if (page == null || page < 1) {
            page = 1;
        }
        return R.ok(modelService.getListByIsAnonymousness(userInfo.getId(),page));
    }

    //这个主要是为了给其他服务调用写的 匿名模块 用openfeign进行调用
    //返回一个list 页面上不能显示我也不知道为什么
    @GetMapping("/private/my")
    public List<Model> privateModel(@RequestParam("page") Integer page,
                          @RequestParam("userId") Long userId){
        if (page == null || page < 1) {
            page = 1;
        }
        PageInfo<Model> privateModels = modelService.
                getListByIsAnonymousness(userId, page);

        return privateModels.getList();
    }

    //查询所有的匿名 所有的所有的所有的匿名信息显示在树洞模块 重要的事情说三遍
    @GetMapping("/private/all")
    public R<Map<String, Object>> privateAllModel(Integer page){
        if (page == null || page < 0){
            page =1;
        }
        PageInfo<Model> privateAll = modelService.getPrivateAll(page);
        Map<String, Object> map = new HashMap<>();
        map.put("privateAll", privateAll);
        return R.ok("匿名的消息为：", map);
    }

    //feign 为了给es创建索引用的
    @GetMapping("/private/feignAll")
    public List<Model> privateFeignAll(Integer page){
        if (page == null || page < 0){
            page =1;
        }
        PageInfo<Model> privateAll = modelService.getPrivateAll(page);
        return privateAll.getList();
    }

    //辅导区的动态获取 所有的
    @GetMapping("/soul/model")
    public  R<List<Model>> soulListModels(@RequestParam("page") Integer page){
        if (page == null || page < 1) {
            page = 1;
        }
        return R.ok(modelService.getSoulList(page));
    }

    //给es创建索引的时候用
    @GetMapping("/soul/feignAll")
    public List<Model> soulFeignAll(Integer page){
        if (page == null || page < 1) {
            page = 1;
        }
        return modelService.getSoulList(page).getList();
    }


    /**
     * 模块增删改
     * @param modelId
     * @param userInfo
     * @return
     */
    //http:localhost:20000/api/v1/models/6/delete
    @GetMapping("/{modelId}/delete")
    public R<Model> delete(@PathVariable("modelId") Integer modelId,
                           @AuthenticationPrincipal UserInfo userInfo) {
        System.out.println(userInfo);
        Model model = modelService.delete(modelId, userInfo.getId(), userInfo.getRole());
        return R.ok(model);
    }

    @PostMapping("/{modelId}/update")
    public R<Model> update(@PathVariable("modelId") Integer modelId,
                           @AuthenticationPrincipal UserInfo userInfo,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content){
        Model model = modelService.update(modelId,userInfo.getId(),title,content);
        return R.ok(model);
    }

    @GetMapping("/{id}")
    public R<Model> getModelById(@PathVariable("id") Integer id) {
        return R.ok(modelService.getModelById(id));
    }

    @RequestMapping("/{modelId}/like")
    public R<Integer> updateLikes(@PathVariable("modelId") Integer modelId){
        int row = modelService.updateLikes(modelId);
        return R.ok(row);
    }

    @RequestMapping("/{modelId}/dislike")
    public R<Integer> updateDislikes(@PathVariable("modelId") Integer modelId){
        int row = modelService.updateDislikes(modelId);
        return R.ok(row);
    }

}