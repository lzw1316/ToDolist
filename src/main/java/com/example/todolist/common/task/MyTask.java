package com.example.todolist.common.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyTask {

    @Scheduled(cron = "0/5 * * * * * ")
    public void test(){
//        log.info("定时任务开始执行：{}",new Date());
    }

    //根据账号获取此账号对应的任务，判断是否需要提醒超时

    public void processNoSuccessTask(){
        //先查询此刻账号对应的任务

    }
}
