package com.intelligent.cloud_picture_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.intelligent.cloud_picture_backend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class IntelligentPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentPictureBackendApplication.class, args);
    }

}
