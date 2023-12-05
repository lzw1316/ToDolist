package com.example.todolist;

import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.properties.SendSmsProperties;
import com.example.todolist.common.utils.SendSmsUtils;
import com.example.todolist.server.mapper.UserEmailMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToDolistApplicationTests {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Resource
//    private TaskMapper taskMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private SendSmsProperties sendSmsProperties;
//    @Autowired
//    private JwtProperties jwtProperties;
//    @Test
//    public void contextLoads() throws Exception{
////        SendSmsUtils.createSms(sendSmsProperties.getEndpoint(),sendSmsProperties.getAccessKeyId(),
////                sendSmsProperties.getAccessKeySecret(),null);
//        System.out.println(LocalDateTime.now());
//        UserDto account = userMapper.selectByAccount("123456");
//        if (account!=null){
//            System.out.println("1111");
//        }
//        else System.out.println("========");
//    }
//
//    @Test
//    public void clientByGet(){
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String encodedKey = io.jsonwebtoken.io.Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println(secretKey);
//        System.out.println(encodedKey);
//    }
//
//    @Test
//    public void test(){
//        List<TaskPO> pos = taskMapper.AllTask("qqq");
//        System.out.println(pos.get(0));
//        int size = pos.size();
//        System.out.println(size);
//    }
//
//    @Test
//    public void regex(){
//        String str="^(1[3-9]{2}\\d{8}$)";
//        boolean matches = "17796224061".matches(str);
//        System.out.println(matches);
//        System.out.println("11111111111".matches(str));
//        System.out.println("1779622406".matches(str));
//        System.out.println("22222222222".matches(str));
//
//        String email="([a-zA-Z0-9])+@([a-zA-Z0-9])+\\.([a-zA-Z0-9])+";
//        System.out.println("2228315291@qq.com".matches(email));
//        System.out.println("2228315291".matches(email));
//        System.out.println("222222@qq".matches(email));
//
//        String pass="^(\\w{8,}$)";
//        System.out.println("2ss2_89a".matches(pass));
//        System.out.println("ssssssssssssssssss".matches(pass));
//        System.out.println("ssssss".matches(pass));
//    }
//
//    @Autowired
//    private UserEmailServiceImpl emailService;
//
//    @Test
//    public void sendVerificationCode(){
//        emailService.sendVerificationCode("2228315291@qq.com");
//    }

    @Autowired
    private JwtProperties jwtProperties;


    @Autowired
    private UserEmailMapper userEmailMapper;

    @Resource
    private SendSmsUtils sendSmsUtils;
    @Autowired
    private SendSmsProperties sendSmsProperties;

    @Test
    public void test() throws Exception {
//        OgnlSecurityManagerFactory factory = new OgnlSecurityManagerFactory();
//        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:UserEmailMapper.xml");
//        String sms = sendSmsUtils.taskNoSuccess(sendSmsProperties.getEndpoint(), sendSmsProperties.getAccessKeyId(),
//                sendSmsProperties.getAccessKeySecret(), "19830234860","1111");

    }






}
