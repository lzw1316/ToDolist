package com.example.todolist.server.service.impl;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.exception.NotFountByTask;
import com.example.todolist.common.utils.BaseUtils;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.server.mapper.StatusMapper;
import com.example.todolist.server.mapper.TaskMapper;
import com.example.todolist.server.service.StatusService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatusImpl implements StatusService {

    @Resource
    private StatusMapper statusMapper;

    @Resource
    private TaskMapper taskMapper;

    //标志任务完成
    public boolean statusToSuccess(Integer id){
        //先查询完成的是哪个任务
        TaskPO taskPO = taskMapper.selectById(id, BaseUtils.getCurrentAccount());
        if (taskPO!=null){
            //修改状态和更改时间
            taskPO.setStatus(StatusConstant.Status_success);
            taskPO.setUpdateTime(LocalDateTime.now());
            //传入
            int status = statusMapper.statusToSuccess(taskPO);
            if (status==1){
                return true;
            }
            return false;
        }
        throw new NotFountByTask("没有该任务");
    }


    //根据任务完成状态分类
    @Override
    public List<TaskDTO> selectByStatus(Integer status) {
        List<TaskDTO> list=new ArrayList<>();
        for (TaskPO po : statusMapper.selectByStatus(status,BaseUtils.getCurrentAccount())) {
            TaskDTO taskDTO=TaskDTO.builder()
                    .id(po.getId())
                    .content(po.getContent())
                    .updateTime(po.getUpdateTime())
                    .status(po.status)
//                    .label(po.getLabel())
                    .build();
            list.add(taskDTO);
        }
        return list;
    }
}
