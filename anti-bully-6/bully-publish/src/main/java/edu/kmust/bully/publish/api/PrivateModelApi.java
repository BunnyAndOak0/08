package edu.kmust.bully.publish.api;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author BunnyAndOak0
 * @Description 使用OpenFeign远程调用这些服务 匿名模块
 * @Date 2020/8/30 9:08
 **/
public interface PrivateModelApi {
    //树洞个人匿名模块的展示
    @GetMapping("/publish/models/private/my")
    List<Model> privateModel(@RequestParam("page") Integer page,
                                       @RequestParam("userId") Long userId);

    @GetMapping("/publish/models/private/all")
    R<Map<String, Object>> privateAllModel(@RequestParam("page") Integer page);

    @GetMapping("/publish/models/private/feignAll")
    List<Model> privateFeignAll(@RequestParam("page")Integer page);

    @GetMapping("/publish/models/{id}")
    public R<Model> getModelById(@PathVariable("id") Integer id);
}
