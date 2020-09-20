package edu.kmust.bully.diary.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.text.SimpleDateFormat;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/13 16:02
 **/
@Configuration
public class DiaryConfig {
    /**
     * 配置SpringMVC文件上传的默认大小
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){

        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize(DataSize.ofMegabytes(500));
        factory.setMaxRequestSize(DataSize.ofMegabytes(500));

        return factory.createMultipartConfig();
    }

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy/MM/dd");
    }
}
