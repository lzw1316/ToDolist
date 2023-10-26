package com.example.todolist;

import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.server.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToDolistApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtProperties jwtProperties;
    @Test
    public void contextLoads() {
        System.out.println(jwtProperties);

    }

}
