package com.example.todolist;

import com.example.todolist.server.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToDolistApplicationTests {

    @Autowired
    private TaskService taskService;

    @Test
    public void contextLoads() {


    }

}
