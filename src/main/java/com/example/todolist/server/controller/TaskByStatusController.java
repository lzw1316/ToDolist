package com.example.todolist.server.controller;

import com.example.todolist.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

//@RestController
//@RequestMapping("/status")
@Slf4j
public class TaskByStatusController {

    @Autowired
    private TaskService taskService;

}
