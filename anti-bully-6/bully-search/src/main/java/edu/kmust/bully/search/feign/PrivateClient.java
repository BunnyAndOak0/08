package edu.kmust.bully.search.feign;

import edu.kmust.bully.publish.api.PrivateModelApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/31 7:46
 **/
@FeignClient("bully-publish")
@Component
public interface PrivateClient extends PrivateModelApi {
}
