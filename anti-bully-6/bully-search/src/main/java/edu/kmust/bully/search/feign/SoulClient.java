package edu.kmust.bully.search.feign;

import edu.kmust.bully.publish.api.SoulModelApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/9/3 12:52
 **/
@Component
@FeignClient("bully-publish")
public interface SoulClient extends SoulModelApi {
}
