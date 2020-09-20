package edu.kmust.bully.publish.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/20 1:51
 **/
@Component
public class RedisForbbidenUtils {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public static final String FORBIDDEN_USER = "FORBIDDEN_USER:";

    //返回true为没有禁言 false为已经被禁言
    public boolean forbidden(String username){
        String user = stringRedisTemplate.opsForValue().get(FORBIDDEN_USER + username);
        if (user == null){
            return true;
        }else{
            return false;
        }
    }
}
