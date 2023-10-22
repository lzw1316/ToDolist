package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.TaskByStatusDto;
import com.example.todolist.pojo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    List<TaskDTO> AllTask();


    TaskDTO selectByContent(String content);

    boolean addTask(TaskDTO taskDTO);

    int statusToSuccess(TaskDTO taskDTO);


    boolean updateByTask(TaskByStatusDto taskByStatusDto);
}
