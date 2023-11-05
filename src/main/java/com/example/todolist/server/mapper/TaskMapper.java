package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.ProcessPo;
import com.example.todolist.pojo.po.TaskPO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskMapper {

    //查询所有任务
    @Select("select * from task  where account=#{account} order by serial_number asc")
    List<TaskPO> AllTask(String account);

    @Select("select * from task where id =#{id} and account=#{account}")
    TaskPO selectByContent(Integer id,String account);

    //添加任务

    @Insert("""
            insert into task(content,account,create_time,update_time,status,label,serial_number) 
            values(#{content},#{account},#{createTime},#{updateTime},#{status},#{label},#{serialNumber})
            """)
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertTask(TaskDTO taskDTO);

    //编辑任务
//    @Update("update task set content=#{content},label=#{label},update_time=#{updateTime},status=#{status} where id=#{id}")
    int updateByContent(TaskPO taskPO);

    //删除一个或者多个任务
    int deleteByTasks(List<Integer> ids,String account);

    //分页查询
    Page<TaskDTO> selectByPage(PageDto pageDto);

    //拖拽排序
    @Update("update task set serial_number=#{serialNumber} where id=#{id}")
    int selectSortBySerial(TaskPO taskPO);

    //过滤任务
    List<TaskPO> filterByTask(TaskDTO taskDTO);

    //查询是否有经过24h且未完成的任务
    //select * from task where account=#{account} and create_time < #{time} and status=#{status};
    //select t.content,u.phone from task t left join user u on t.account=u.account
//    @Select("""
//            select * from task where account=#{account} and create_time < #{time} and status=#{status};
//            """)
    @Select("""
            select t.account,u.phone from task t left join user u on t.account=u.account 
            where  t.create_time < #{time} and t.status=#{status}
            """)
    List<ProcessPo> processNoSuccess(LocalDateTime time, Integer status);
}
