package com.example.todolist.server.controller;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.TaskByStatusDto;
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
    @GetMapping("")
    public Result queryAllTask(){
        List<TaskDTO> dtos = taskService.AllTask();
        return Result.success(dtos);
    }

    //新增任务，默认status为0,表示任务未完成
    @PostMapping("")
    public Result addTask(@RequestBody TaskDTO taskDTO){
        log.info("新增任务的信息：{}",taskDTO);
        boolean i = taskService.addTask(taskDTO);
        if (i==true){
            return Result.success();
        }
        return Result.error("任务添加失败");
    }

    //根据任务内容编辑任务，修改任务，此时修改后日期应为修改时的时间
    //先将查询的内容对应的任务返回给客户端
    @GetMapping("/{content}")
    public Result queryTaskByContent(@PathVariable String content){
        log.info("查询的内容：{}",content);
        //查询此内容所在的整体数据
        TaskDTO taskDTO = taskService.selectByContent(content);
        return Result.success(taskDTO);
    }
    //由客户端传输新的内容编辑任务内容
    @PutMapping("")
    public Result editTask(TaskByStatusDto taskByStatusDto){
        log.info("进行编辑的内容：{}",taskByStatusDto);
        boolean i = taskService.updateByTask(taskByStatusDto);
        return Result.success();
    }


    //标志任务完成，通过传输status=1表示完成，可以动态输入1，方便后期维护
    @GetMapping("/status/{content}")
    public Result successByTask(@PathVariable String content) {
        log.info("测试任务content：{}",content);
        //查询完成的是哪个任务
        TaskDTO dto = taskService.selectByContent(content);
        //传输status=1
        int status = taskService.statusToSuccess(dto);
        if (status == StatusConstant.Status_success) {
            return Result.success();
        }
        return Result.error("传输不成功");
    }
}
