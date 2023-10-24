package com.example.todolist.server.service;

import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskByStatusDto;
import com.example.todolist.pojo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    //查询所有任务
    List<TaskDTO> AllTask();

    //根据内容查询任务
    TaskDTO selectByContent(String content);

    //增加任务
    boolean addTask(TaskDTO taskDTO);

    //修改任务状态
    int statusToSuccess(TaskDTO taskDTO);

    //更改任务
    boolean updateByTask(TaskByStatusDto taskByStatusDto);

    //删除任务
    boolean deleteByTasks(List<Integer> ids);

    //根据status进行分类
    List<TaskDTO> selectByStatus(Integer status);

    //分页查询
    Result queryByPage(PageDto pageDto);

    //可拖放排序
    List<TaskDTO> sortBySerial(Integer startNumber,Integer endNumber);
}
