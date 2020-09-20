package edu.kmust.bully.search.feign;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.publish.api.PublicModelApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/31 7:46
 **/
@FeignClient("bully-publish")
@Component
public interface PublicClient extends PublicModelApi {
}
