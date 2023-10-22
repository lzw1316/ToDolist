package com.example.todolist.server.service.impl;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.exception.NotFountByTask;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.TaskByStatusPo;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.server.mapper.TaskMapper;
import com.example.todolist.server.service.TaskService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Resource
    private TaskMapper taskMapper;

    //查询所有任务
    @Override
    public List<TaskDTO> AllTask() {
        List<TaskDTO> list=new ArrayList<>();
        List<TaskPO> taskPOS = taskMapper.AllTask();
        for (TaskPO taskPO : taskPOS) {
            TaskDTO taskDTO=new TaskDTO();
            BeanUtils.copyProperties(taskPO,taskDTO);
            list.add(taskDTO);
        }
        return list;
    }

    //查询某个任务
    public TaskDTO selectTaskByContent(TaskDTO taskDTO){
        for (TaskPO taskPO : taskMapper.AllTask()) {
            if (taskPO.getContent().equals(taskDTO.getContent())){
                return taskDTO;
            }
        }
        //循环结束后没返回，表示没有此任务
        throw new NotFountByTask("没有此任务");
    }

    //添加任务
    @Override
    public boolean addTask(TaskDTO taskDTO) {
        log.info("新增任务的信息：{}",taskDTO);
        TaskPO taskPO=new TaskPO();
        BeanUtils.copyProperties(taskDTO,taskPO);
        int bool=taskMapper.insertTask(taskPO);
        if (bool==1){return true;}
        else {return false;}
    }

    //设置status状态
    public int statusToSuccess(TaskDTO taskDTO){
        TaskByStatusPo po=TaskByStatusPo.builder()
                .content(taskDTO.getContent())
                .createTime(taskDTO.getCreateTime())
                .status(StatusConstant.Status_success)
                .build();
        int i = taskMapper.statusToSuccess(po);
        return i;
    }
}
