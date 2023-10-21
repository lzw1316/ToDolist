package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.TaskDTO;

public interface TaskService {

    boolean addTask(TaskDTO taskDTO);
}
