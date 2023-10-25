package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.TaskDTO;

import java.util.List;

public interface StatusService {

    //修改任务状态
    int statusToSuccess(Integer id);

    //根据status进行分类
    List<TaskDTO> selectByStatus(Integer status);
}
