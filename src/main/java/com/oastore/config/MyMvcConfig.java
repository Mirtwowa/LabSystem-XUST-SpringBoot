package com.oastore.config;

import com.oastore.interceptors.LoginInterceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/", "/userLogin", "/register", "/api/code/simple/send", "/resetPwd");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        // 根据需要开启或关闭 allowCredentials
        // config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 配置 Redis 服务器地址和端口等
        config.useSingleServer().setAddress("127.0.0.1:6379");
        // 可以根据需要添加更多配置，如密码、连接池设置等
        // 例如：config.useSingleServer().setPassword("yourpassword").setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}