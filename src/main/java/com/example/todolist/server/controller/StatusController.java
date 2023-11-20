package com.example.todolist.server.controller;

import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.vo.StatusVo;
import com.example.todolist.server.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务状态
 */
@RestController
@RequestMapping("/status")

@Slf4j
public class StatusController {

    @Autowired
    private StatusService statusService;


    //标志任务完成，通过传输status=1表示完成
    /**
     * 标志任务已完成
     * @param id
     * @return
     */
    @PutMapping("")
    public Result successByTask(@RequestParam Integer id) {
        log.info("任务id：{}",id);
        //传输id
        boolean status = statusService.statusToSuccess(id);
        if (status == true) {
            return Result.success("该任务已完成");
        }
        return Result.error("没有该任务");
    }

    //根据status进行任务分类，0分配成未完成，1则为完成，-1为超时
    /**
     * 根据status进行任务分类
     * @param number
     * @return
     */
    @GetMapping()
    public Result sortByStatus(@RequestParam Integer number){
        log.info("查询的状态：{}",number);
        List<StatusVo> list=new ArrayList<>();
        for (TaskDTO dto : statusService.selectByStatus(number)) {
            StatusVo vo = new StatusVo();
            BeanUtils.copyProperties(dto, vo);
            list.add(vo);
        }
        return Result.success(list);
    }


}
