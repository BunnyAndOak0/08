package edu.kmust.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Author BunnyAndOak0
 * @Description 配置允许跨域
 * @Date 2020/8/21 11:39
 **/
@Configuration
public class BullyCorsConfig {
//    @Bean
//    public CorsWebFilter corsWebFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        //1、配置跨域
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("*");
////        http://f3346291w6.zicp.vip/  这个是公网ip
////        corsConfiguration.addAllowedOrigin("http://f3346291w6.zicp.vip/");
//        corsConfiguration.setAllowCredentials(true);
//
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsWebFilter(source);
//    }
}
