package com.example.todolist;

import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.server.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ToDolistApplicationTests {

    @Autowired
    private TaskService taskService;
    @Test
    void contextLoads() {
        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setContent("每天按时学习");
        taskDTO.setCreateTime(LocalDateTime.now());
        taskService.addTask(taskDTO);
    }

}
