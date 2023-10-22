package com.example.todolist.server.mapper;

import com.example.todolist.pojo.po.TaskByStatusPo;
import com.example.todolist.pojo.po.TaskPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TaskMapper {

    //todo:后期使用动态sql
    //查询所有任务
    @Select("select * from task")
    List<TaskPO> AllTask();

    @Select("select * from task where content =#{content}")
    TaskPO selectByContent(String content);
    //添加任务
    @Insert("insert into task(content,create_time) values(#{content},#{createTime})")
    int insertTask(TaskByStatusPo taskByStatusPo);

    @Update("update task set status=#{status} where content=#{content}")
    int statusToSuccess(TaskByStatusPo taskByStatusPo);

    //todo：后期需设置where条件
    @Update("update task set content=#{content},create_time=#{createTime},status=#{status} where id=#{id}")
    int updateByContent(TaskByStatusPo taskByStatusPo);
}
