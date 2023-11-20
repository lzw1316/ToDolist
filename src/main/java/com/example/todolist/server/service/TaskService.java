package com.example.todolist.server.service;

import com.example.todolist.common.result.PageResult;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;

import java.util.List;
import java.util.Map;

public interface TaskService {

    //查询所有任务
    List<TaskDTO> AllTask(String account);

    //根据内容模糊查询任务
    List<TaskDTO> selectByContent(String content);

    //增加任务
    Map addTask(TaskDTO taskDTO);


    //更改任务
    boolean updateByTask(TaskDTO taskDTO);

    //删除任务
    boolean deleteByTasks(List<Integer> ids);

    //分页查询
    PageResult  queryByPage(PageDto pageDto);

    //可拖放排序
    List<TaskDTO> sortBySerial(Integer startIdNumber,Integer endIdNumber);

    //根据用户传输的某一个信息进行过滤任务返回
    List<TaskDTO> filterByTask(TaskDTO taskDTO);

    //开启定时处理
//   void processNoSuccessTask (String account) throws Exception;
}
