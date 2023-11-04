package com.example.todolist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional
@EnableCaching
@EnableConfigurationProperties
@EnableScheduling
@MapperScan("com.example.todolist.server.mapper")
public class ToDolistApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDolistApplication.class, args);
    }

}
