package edu.kmust.bully.diary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.kmust.bully.diary.mapper")
@EnableRedisHttpSession
public class BullyDiaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BullyDiaryApplication.class, args);
    }
}
