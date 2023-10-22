package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    List<TaskDTO> AllTask();

    TaskDTO selectTaskByContent(TaskDTO taskDTO);

    boolean addTask(TaskDTO taskDTO);

    int statusToSuccess(TaskDTO taskDTO);


}
