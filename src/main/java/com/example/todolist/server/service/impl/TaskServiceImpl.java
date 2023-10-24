package com.example.todolist.server.service.impl;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.exception.CantAddByTask;
import com.example.todolist.common.result.Result;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskByStatusDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.TaskByStatusPo;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.server.mapper.TaskMapper;
import com.example.todolist.server.service.TaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Resource
    private TaskMapper taskMapper;

    //查询所有任务
    @Override
    public List<TaskDTO> AllTask() {
        List<TaskDTO> list=new ArrayList<>();
        List<TaskPO> taskPOS = taskMapper.AllTask();
        for (TaskPO taskPO : taskPOS) {
            TaskDTO taskDTO=new TaskDTO();
            BeanUtils.copyProperties(taskPO,taskDTO);
            list.add(taskDTO);
        }
        return list;
    }

    //查询某个任务
    public TaskDTO selectByContent(String content){
        TaskDTO dto=new TaskDTO();
        TaskPO taskPO = taskMapper.selectByContent(content);
        BeanUtils.copyProperties(taskPO,dto);
        return dto;
    }

    //添加任务
    @Override
    public boolean addTask(TaskDTO taskDTO) {
        log.info("新增任务的信息：{}",taskDTO);
        TaskByStatusPo taskByStatusPo=new TaskByStatusPo();
        BeanUtils.copyProperties(taskDTO,taskByStatusPo);
        taskByStatusPo.setCreateTime(LocalDateTime.now());
        taskByStatusPo.setStatus(StatusConstant.Status_fail);
        int bool=taskMapper.insertTask(taskByStatusPo);
        if (bool==1){return true;}
        else {throw new CantAddByTask("任务添加失败");}
    }

    //设置status状态
    public int statusToSuccess(TaskDTO taskDTO){
        TaskByStatusPo po=TaskByStatusPo.builder()
                .content(taskDTO.getContent())
                .createTime(taskDTO.getCreateTime())
                .status(StatusConstant.Status_success)
                .build();
        int status = taskMapper.statusToSuccess(po);
        return status;
    }

    //编辑某个任务内容
    @Override
    public boolean updateByTask(TaskByStatusDto taskByStatusDto) {
        TaskByStatusPo taskByStatusPo=new TaskByStatusPo();
        BeanUtils.copyProperties(taskByStatusDto,taskByStatusPo);
        taskByStatusPo.setCreateTime(LocalDateTime.now());
        int i = taskMapper.updateByContent(taskByStatusPo);
        if (i==1){
            return true;
        }
        return false;
    }

    //删除任务
    @Override
    public boolean deleteByTasks(List<Integer> ids) {
        int i = taskMapper.deleteByTasks(ids);
        if (i==1){return true;}
        else return false;
    }

    //根据任务找他分类
    @Override
    public List<TaskDTO> selectByStatus(Integer status) {
        List<TaskDTO> list=new ArrayList<>();
        List<TaskByStatusPo> pos = taskMapper.selectByStatus(status);
        for (TaskByStatusPo po : pos) {
            TaskDTO taskDTO=TaskDTO.builder()
                    .content(po.getContent())
                    .createTime(po.getCreateTime())
                    .build();
            list.add(taskDTO);
        }
        return list;
    }

    @Override
    public Result queryByPage(PageDto pageDto) {
        PageHelper.startPage(pageDto.getIndex(),pageDto.getPages());
        Page<TaskByStatusDto> pages= taskMapper.selectByPage(pageDto);
        return Result.success(pages.getResult());
    }

    @Override
    //拖拽排序
    // TODO: 2023/10/24 后期需要增加动态sql一键修改值
    public List<TaskDTO> sortBySerial(Integer startNumber,Integer endNumber) {
//        List<TaskPO> list=new ArrayList<>();
        //先查询出全部任务
        for (TaskPO taskPO : taskMapper.AllTask()) {
            //查询number对应此刻的任务
            if (taskPO.getSerialNumber()>=startNumber&&taskPO.getSerialNumber()<endNumber){
                taskPO.setSerialNumber(taskPO.getSerialNumber()+1);
//                list.add(taskPO);
                taskMapper.selectSortBySerial(taskPO);
            }
            else if (taskPO.getSerialNumber()==endNumber){
                taskPO.setSerialNumber(startNumber);
//                list.add(taskPO);
                taskMapper.selectSortBySerial(taskPO);
            }
        }
        //将list插入mapper里进行动态修改
//        taskMapper.selectSortBySerial(list);
        //再查询全部任务返回给客户端
        List<TaskDTO> list1=new ArrayList<>();
        for (TaskPO po : taskMapper.AllTask()) {
            TaskDTO dto=new TaskDTO();
            BeanUtils.copyProperties(po,dto);
            list1.add(dto);
        }
        return list1;
    }
}
