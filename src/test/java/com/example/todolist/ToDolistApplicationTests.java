package com.example.todolist;

import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.properties.SendSmsProperties;
import com.example.todolist.pojo.dto.UserDto;
import com.example.todolist.pojo.po.TaskPO;
import com.example.todolist.server.mapper.TaskMapper;
import com.example.todolist.server.mapper.UserMapper;
import com.example.todolist.server.service.TaskService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ToDolistApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendSmsProperties sendSmsProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Test
    public void contextLoads() throws Exception{
//        SendSmsUtils.createSms(sendSmsProperties.getEndpoint(),sendSmsProperties.getAccessKeyId(),
//                sendSmsProperties.getAccessKeySecret(),null);
        System.out.println(LocalDateTime.now());
        UserDto account = userMapper.selectByAccount("123456");
        if (account!=null){
            System.out.println("1111");
        }
        else System.out.println("========");
    }

    @Test
    public void clientByGet(){
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encodedKey = io.jsonwebtoken.io.Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(secretKey);
        System.out.println(encodedKey);
    }

    @Test
    public void test(){
        List<TaskPO> pos = taskMapper.AllTask("qqq");
        System.out.println(pos.get(0));
        int size = pos.size();
        System.out.println(size);
    }

}
