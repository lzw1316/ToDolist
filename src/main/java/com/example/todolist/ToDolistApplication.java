package com.example.todolist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional
@MapperScan("com.example.todolist.server.mapper")
public class ToDolistApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDolistApplication.class, args);
    }

}
