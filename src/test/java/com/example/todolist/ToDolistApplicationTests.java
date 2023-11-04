package com.example.todolist;

import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.properties.SendSmsProperties;
import com.example.todolist.server.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ToDolistApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SendSmsProperties sendSmsProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Test
    public void contextLoads() throws Exception{
//        SendSmsUtils.createSms(sendSmsProperties.getEndpoint(),sendSmsProperties.getAccessKeyId(),
//                sendSmsProperties.getAccessKeySecret(),null);
        System.out.println(LocalDateTime.now());

    }

}
