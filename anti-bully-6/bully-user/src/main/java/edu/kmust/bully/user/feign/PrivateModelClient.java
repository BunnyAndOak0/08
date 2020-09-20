package edu.kmust.bully.user.feign;

import edu.kmust.bully.publish.api.PrivateModelApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description 远程调用微服务
 * @Date 2020/8/30 9:30
 **/
@FeignClient("bully-publish")
@Component
public interface PrivateModelClient extends PrivateModelApi {
}
