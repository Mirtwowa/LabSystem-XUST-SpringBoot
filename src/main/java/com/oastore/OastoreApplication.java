package com.oastore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oastore.mapper")
public class OastoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OastoreApplication.class, args);
    }

}
