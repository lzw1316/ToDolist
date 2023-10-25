package com.example.todolist.server.controller;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.server.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
@Slf4j
public class StatusController {

    @Autowired
    private StatusService statusService;


    //标志任务完成，通过传输status=1表示完成
    @PutMapping("/id")
    public Result successByTask(@RequestParam Integer number) {
        log.info("任务id：{}",number);
        //传输id
        int status = statusService.statusToSuccess(number);
        if (status == StatusConstant.Status_success) {
            return Result.success();
        }
        return Result.error("传输不成功");
    }

    //根据status进行任务分类，0分配成未完成，1则为完成
    // TODO: 2023/10/23  后期需要设置status常量，方便后期修改,   返回标签
    @GetMapping("/{success}")
    public Result sortByStatus(@PathVariable Integer success){
        log.info("查询的状态：{}",success);
        List<TaskDTO> dtos = statusService.selectByStatus(success);
        return Result.success(dtos);
    }

}
