package edu.kmust.bully.user.feign;

import edu.kmust.bully.publish.api.PublicModelApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/30 9:30
 **/
@Component
@FeignClient("bully-publish")
public interface PublicModelClient extends PublicModelApi {
}
