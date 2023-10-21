package com.example.todolist.server.service.impl;

import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.server.mapper.TaskMapper;
import com.example.todolist.server.service.TaskService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Override
    public boolean addTask(TaskDTO taskDTO) {
        log.info("新增任务的信息：{}",taskDTO);
        TaskPO taskPO=new TaskPO();
        BeanUtils.copyProperties(taskDTO,taskPO);
        int bool=taskMapper.insertTask(taskPO);
        if (bool==1){return true;}
        else {return false;}
    }
}
