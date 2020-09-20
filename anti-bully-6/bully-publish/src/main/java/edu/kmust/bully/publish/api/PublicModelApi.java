package edu.kmust.bully.publish.api;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author BunnyAndOak0
 * @Description 公开模块
 * @Date 2020/8/30 9:08
 **/
public interface PublicModelApi {
    //主页公开动态的个人查询
    @GetMapping("/publish/models/public/my")
    List<Model> publicModel(@RequestParam("page") Integer page,
                                      @RequestParam("userId")Long userId);

    @GetMapping("/publish/models/public/all")
    R<Map<String, Object>> publicAllModel(@RequestParam("page")Integer page);

    @RequestMapping("/publish/models/getPublicById/{id}")
    public R<Map<String, Object>> getPublicById(@PathVariable("id") Integer id);

    @GetMapping("/publish/models/public/feignAll")
    public List<Model> publicFeignAllModel(@RequestParam("page")Integer page);

    @GetMapping("/publish/models/{id}")
    public R<Model> getModelById(@PathVariable("id") Integer id);
}
