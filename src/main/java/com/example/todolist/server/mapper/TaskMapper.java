package com.example.todolist.server.mapper;

import com.example.todolist.pojo.po.TaskByStatusPo;
import com.example.todolist.pojo.po.TaskPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TaskMapper {

    //查询所有任务
    @Select("select * from task")
    List<TaskPO> AllTask();
    //添加任务
    @Insert("insert into task(content,create_time) values(#{content},#{createTime})")
    int insertTask(TaskPO taskPO);

    @Update("update task set status=#{status} where content=#{content}")
    int statusToSuccess(TaskByStatusPo taskByStatusPo);
}
