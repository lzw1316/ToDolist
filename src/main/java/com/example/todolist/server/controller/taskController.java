package com.example.todolist.server.controller;

import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@Slf4j
public class taskController {

    @Autowired
    private TaskService taskService;

    //TODO:后期增加i=0的错误信息
    @PostMapping("")
    public Result addTask(@RequestBody TaskDTO taskDTO){
        log.info("新增任务的信息：{}",taskDTO);
        boolean i = taskService.addTask(taskDTO);
        return Result.success();
    }
}
