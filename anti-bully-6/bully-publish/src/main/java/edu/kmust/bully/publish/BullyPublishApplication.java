package edu.kmust.bully.publish;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.kmust.bully.publish.mapper")
@EnableRedisHttpSession
public class BullyPublishApplication {
    public static void main(String[] args) {
        SpringApplication.run(BullyPublishApplication.class, args);
    }
}

