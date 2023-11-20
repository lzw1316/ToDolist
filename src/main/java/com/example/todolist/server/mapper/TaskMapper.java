package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.ProcessPo;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.pojo.vo.TaskVO;
import com.github.pagehelper.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskMapper {

    //查询所有任务
    List<TaskPO> AllTask(String account);

    //根据内容模糊查询任务
    List<TaskPO> selectByContent(String content, String account);

    //根据id查询任务
    TaskPO selectById(Integer id, String account);

    //添加任务
    int insertTask(TaskDTO taskDTO);

    //编辑任务
    int updateByContent(TaskPO taskPO);

    //删除一个或者多个任务
    int deleteByTasks(List<Integer> ids, String account);

    //分页查询
    Page<TaskVO> selectByPage(PageDto pageDto);

    //查询id对应的serialNumber
    Integer selectSerialById(Integer id);

    //拖拽排序
    int selectSortBySerial(TaskPO taskPO);

    //过滤任务
    List<TaskPO> filterByTask(TaskDTO taskDTO);

    //查询是否有经过24h且未完成的任务
//    @Select("""
//            select t.account,u.phone from task t left join user u on t.account=u.account
//            where  t.create_time < #{time} and t.status=#{status}
//            """)
    List<ProcessPo> processNoSuccess(LocalDateTime time, Integer status);
}
