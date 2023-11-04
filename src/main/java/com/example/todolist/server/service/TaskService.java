package com.example.todolist.server.service;

import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    //查询所有任务
    List<TaskDTO> AllTask(String account);

    //根据id查询任务
    TaskDTO selectByContent(Integer id);

    //增加任务
    boolean addTask(TaskDTO taskDTO);

    //更改任务
    boolean updateByTask(TaskDTO taskDTO);

    //删除任务
    boolean deleteByTasks(List<Integer> ids);

    //分页查询
    Result queryByPage(PageDto pageDto);

    //可拖放排序
    List<TaskDTO> sortBySerial(Integer startNumber,Integer endNumber);

    //根据用户传输的某一个信息进行过滤任务返回
    List<TaskDTO> filterByTask(TaskDTO taskDTO);
}
