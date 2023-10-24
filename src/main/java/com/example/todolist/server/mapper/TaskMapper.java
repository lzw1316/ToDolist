package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskByStatusDto;
import com.example.todolist.pojo.po.TaskByStatusPo;
import com.example.todolist.pojo.po.TaskPO;
import com.github.pagehelper.Page;
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
    @Insert("insert into task(content,create_time,status,label) values(#{content},#{createTime},#{status},#{label})")
    int insertTask(TaskByStatusPo taskByStatusPo);

    //编辑任务状态
    @Update("update task set status=#{status} where content=#{content}")
    int statusToSuccess(TaskByStatusPo taskByStatusPo);

    //编辑任务
    @Update("update task set content=#{content},create_time=#{createTime},status=#{status} where id=#{id}")
    int updateByContent(TaskByStatusPo taskByStatusPo);

    //删除一个或者多个任务
    int deleteByTasks(List<Integer> ids);

    //对任务进行分类，分为已完成和未完成
    //利用动态sql判断status是否传入的为1或者0
    @Select("select content,create_time from task where status =#{status}")
    List<TaskByStatusPo> selectByStatus(Integer status);


    Page<TaskByStatusDto> selectByPage(PageDto pageDto);
}
