package edu.kmust.bully.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 2:14
 **/
@SpringBootApplication
@MapperScan("edu.kmust.bully.user.mapper")
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableFeignClients(basePackages = "edu.kmust.bully.user.feign")
public class BullyUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BullyUserApplication.class, args);
    }
}
