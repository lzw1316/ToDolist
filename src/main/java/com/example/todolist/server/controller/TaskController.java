package com.example.todolist.server.controller;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    //所有任务展示给前端
    //TODO:后期增加令牌，超过令牌时间则需重新点击任务列表展示按钮
    @GetMapping()
    public Result queryAllTask(){
        List<TaskDTO> dtos = taskService.AllTask();
        return Result.success(dtos);
    }

    //TODO:后期增加i=0的错误信息
    //新增任务
    @PostMapping("")
    public Result addTask(@RequestBody TaskDTO taskDTO){
        log.info("新增任务的信息：{}",taskDTO);
        boolean i = taskService.addTask(taskDTO);
        return Result.success();
    }

    //标志任务完成，通过传输status=1表示完成，可以动态输入1，方便后期维护
    @PutMapping("")
    public Result successByTask(TaskDTO taskDTO) {
        //查询完成的是哪个任务,数据库中没有此任务的话会抛出异常
        TaskDTO dto = taskService.selectTaskByContent(taskDTO);
        //传输status=1
        int status = taskService.statusToSuccess(dto);
        if (status == StatusConstant.Status_success) {
            return Result.success();
        }
        return Result.error("传输不成功");
    }
}
