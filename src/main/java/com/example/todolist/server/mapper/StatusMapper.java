package com.example.todolist.server.mapper;

import com.example.todolist.pojo.po.TaskPO;

import java.util.List;

public interface StatusMapper {

    //编辑任务状态
    int statusToSuccess(TaskPO taskPO);

    //对任务进行分类，分为已完成和未完成
    List<TaskPO> selectByStatus(Integer status,String account);
}
