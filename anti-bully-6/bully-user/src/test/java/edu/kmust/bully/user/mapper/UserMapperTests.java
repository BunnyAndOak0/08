package edu.kmust.bully.user.mapper;

import edu.kmust.bully.common.security.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static edu.kmust.bully.user.service.impl.UserServiceImpl.PREFIX_INFO;

@SpringBootTest
public class UserMapperTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate redisTemplate;


    @Test
    void loadUserByUsername(){
        UserInfo userInfo = userMapper.loadUserByUsername("all");
        System.err.println(userInfo);
    }

    @Test
    public void getNum(){
//        System.out.println((int)(Math.random() * 1000000));

        redisTemplate.opsForValue().set(PREFIX_INFO + "15969485654",
                "123456", 5, TimeUnit.MINUTES);

        System.out.println(redisTemplate.opsForValue().get(PREFIX_INFO + "15969485654"));
    }
}
