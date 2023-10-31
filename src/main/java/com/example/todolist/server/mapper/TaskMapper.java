package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.TaskPO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TaskMapper {

    //todo:后期使用动态sql
    //查询所有任务
    @Select("select * from task order by serial_number asc")
    List<TaskPO> AllTask();

    @Select("select * from task where id =#{id}")
    TaskPO selectByContent(Integer id);

    //添加任务

    // TODO: 2023/10/25 后期 serialNumber自动+1
    @Insert("""
            insert into task(content,create_time,update_time,status,label,serial_number) 
            values(#{content},#{createTime},#{updateTime},#{status},#{label},#{serialNumber})
            """)
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertTask(TaskDTO taskDTO);

    //编辑任务
//    @Update("update task set content=#{content},label=#{label},update_time=#{updateTime},status=#{status} where id=#{id}")
    int updateByContent(TaskPO taskPO);

    //删除一个或者多个任务
    int deleteByTasks(List<Integer> ids);

    //分页查询
    Page<TaskDTO> selectByPage(PageDto pageDto);

    //拖拽排序
    @Update("update task set serial_number=#{serialNumber} where id=#{id}")
    int selectSortBySerial(TaskPO taskPO);

    //过滤任务
    List<TaskPO> filterByTask(TaskDTO taskDTO);
}
