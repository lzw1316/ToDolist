package com.example.todolist.server.service.impl;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.exception.CantAddByTask;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.BaseUtils;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.server.mapper.TaskMapper;
import com.example.todolist.server.service.TaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<TaskDTO> AllTask(String account) {
        List<TaskDTO> list=new ArrayList<>();
        List<TaskPO> taskPOS = taskMapper.AllTask(account);
        for (TaskPO taskPO : taskPOS) {
            TaskDTO taskDTO=new TaskDTO();
            BeanUtils.copyProperties(taskPO,taskDTO);
            list.add(taskDTO);
        }
        return list;
    }

    //查询某个任务
    public TaskDTO selectByContent(Integer id){
        TaskDTO dto=new TaskDTO();
        TaskPO taskPO = taskMapper.selectByContent(id,BaseUtils.getCurrentAccount());
        BeanUtils.copyProperties(taskPO,dto);
        return dto;
    }

    //添加任务
    @Override
    @Transactional
    public boolean addTask(TaskDTO taskDTO) {
        log.info("账号：{}",BaseUtils.getCurrentAccount());
        //查询serialNumber最后一个数字
        List<TaskPO> pos = taskMapper.AllTask(BaseUtils.getCurrentAccount());
        //每次排序自动+1
        if (pos.size()==0){
            taskDTO.setSerialNumber(1);
        }else {
            taskDTO.setSerialNumber(pos.get(pos.size()-1).getSerialNumber()+1);
        }
        taskDTO.setAccount(BaseUtils.getCurrentAccount());

        taskDTO.setCreateTime(LocalDateTime.now());
        taskDTO.setUpdateTime(LocalDateTime.now());
        taskDTO.setStatus(StatusConstant.Status_fail);
        int bool=taskMapper.insertTask(taskDTO);
        if (bool==1){return true;}
        else {throw new CantAddByTask("任务添加失败");}
    }

    //编辑某个任务内容
    @Override
    public boolean updateByTask(TaskDTO taskDTO) {
        TaskPO taskPO=new TaskPO();
        BeanUtils.copyProperties(taskDTO,taskPO);
        taskPO.setUpdateTime(LocalDateTime.now());
        int i = taskMapper.updateByContent(taskPO);
        if (i==1){
            return true;
        }
        return false;
    }

    //删除任务
    @Override
    public boolean deleteByTasks(List<Integer> ids) {
        int i = taskMapper.deleteByTasks(ids,BaseUtils.getCurrentAccount());
        if (i>0){return true;}
        else return false;
    }

    //分页查询
    @Override
    public Result queryByPage(PageDto pageDto) {
        PageHelper.startPage(pageDto.getIndex(),pageDto.getPages());
        Page<TaskDTO> pages= taskMapper.selectByPage(pageDto);
        return Result.success(pages.getResult());
    }

    @Override
    @Transactional
    //拖拽排序
    public List<TaskDTO> sortBySerial(Integer startNumber,Integer endNumber) {
        Integer temp=0;
        //比较大小
        if(startNumber>endNumber){
            temp=startNumber;
            startNumber=endNumber;
            endNumber=temp;
        }
        //先查询出全部任务
        for (TaskPO taskPO : taskMapper.AllTask(BaseUtils.getCurrentAccount())) {
            //对排序的数字重新赋值
            if (taskPO.getSerialNumber()>=startNumber&&taskPO.getSerialNumber()<endNumber){
                taskPO.setSerialNumber(taskPO.getSerialNumber()+1);
                taskMapper.selectSortBySerial(taskPO);
            }
            else if (taskPO.getSerialNumber()==endNumber){
                taskPO.setSerialNumber(startNumber);
                taskMapper.selectSortBySerial(taskPO);
            }
        }
        //再查询全部任务返回给客户端
        List<TaskDTO> dtos=new ArrayList<>();
        for (TaskPO po : taskMapper.AllTask(BaseUtils.getCurrentAccount())) {
            TaskDTO dto=new TaskDTO();
            BeanUtils.copyProperties(po,dto);
            dtos.add(dto);
        }
        return dtos;
    }

    //过滤任务
    @Override
    public List<TaskDTO> filterByTask(TaskDTO taskDTO) {
        List<TaskDTO> list=new ArrayList<>();
        for (TaskPO po : taskMapper.filterByTask(taskDTO)) {
            TaskDTO dto=new TaskDTO();
            BeanUtils.copyProperties(po,dto);
            list.add(dto);
        }
        return list;
    }
}
