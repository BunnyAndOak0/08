package edu.kmust.bully.publish.api;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Author BunnyAndOak0
 * @Description 心灵辅导专区模块
 * @Date 2020/8/30 9:09
 **/
public interface SoulModelApi {
    @GetMapping("/publish/models/soul/feignAll")
    public List<Model> soulFeignAll(@RequestParam("page") Integer page);

    @GetMapping("/publish/models/{id}")
    R<Model> getModelById(@PathVariable("id") Integer id);
}
