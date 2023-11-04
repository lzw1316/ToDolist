package com.example.todolist.server.mapper;

import com.example.todolist.pojo.po.TaskPO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StatusMapper {
    //编辑任务状态
    @Update("update task set status=#{status},update_time=#{updateTime} where id=#{id} and account=#{account}")
    int statusToSuccess(TaskPO taskPO);

    //对任务进行分类，分为已完成和未完成
    //利用动态sql判断status是否传入的为1或者0
    @Select("select content,update_time from task where status =#{status} and account=#{account}")
    List<TaskPO> selectByStatus(Integer status,String account);
}
