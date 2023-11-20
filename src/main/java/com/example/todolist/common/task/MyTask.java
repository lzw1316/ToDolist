package com.example.todolist.common.task;


import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.properties.SendSmsProperties;
import com.example.todolist.common.utils.SendSmsUtils;
import com.example.todolist.pojo.po.ProcessPo;
import com.example.todolist.server.mapper.TaskMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class MyTask {

    @Resource
    private TaskMapper taskMapper;
    @Autowired
    private SendSmsProperties sendSmsProperties;
    @Resource
    private  SendSmsUtils sendSmsUtils;

    //判断是否需要提醒超时
    //如果此任务超过24h未完成，将发送短信提醒1111
    //双表联查，查出该账号对应的手机号码及任务
    @Scheduled(cron = "0 0 0,12 * * ? ")
    public void processNoSuccessTask() throws Exception {
        log.info("定时任务开始执行：{}",LocalDateTime.now());
        //将此刻时间-24h
        LocalDateTime time = LocalDateTime.now().plusHours(-24);

        //查询出未完成的任务
        List<ProcessPo> processPos11 = taskMapper.processNoSuccess(time, StatusConstant.Status_fail);
        log.info("未完成的任务：{}",processPos11);

        //提取不同元素
        Set<ProcessPo> set = new HashSet<>(processPos11);
        List<ProcessPo> uniqueList = new ArrayList<>(set);
        log.info("不同元素集合：{}",uniqueList);

        //依次发送短信
        for (ProcessPo po : uniqueList) {
            String sms = sendSmsUtils.taskNoSuccess(sendSmsProperties.getEndpoint(), sendSmsProperties.getAccessKeyId(),
                    sendSmsProperties.getAccessKeySecret(), po.getPhone(),"1111");
            System.out.println(po.getPhone());
            System.out.println(sms);
        }
        }
    }

