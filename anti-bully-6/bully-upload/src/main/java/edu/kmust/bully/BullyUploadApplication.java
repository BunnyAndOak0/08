package edu.kmust.bully;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/16 4:20
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class BullyUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(BullyUploadApplication.class, args);
    }
}
