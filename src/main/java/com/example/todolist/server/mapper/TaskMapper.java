package com.example.todolist.server.mapper;

import com.example.todolist.pojo.po.TaskPO;
import org.apache.ibatis.annotations.Insert;

public interface TaskMapper {


    @Insert("insert into task(content,create_time) values(#{content},#{createTime})")
    int insertTask(TaskPO taskPO);
}
