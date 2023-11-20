package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.UserDto;

public interface UserService {

    //获取验证码并且存放在redis里
    String sendSms(String tele) throws Exception;

    //修改密码时获取验证码必存储在redis里
    String sendSmsByPassword(String tele) throws Exception;
    //注册用户
    boolean signup(UserDto userDto);

    //校验验证码
    boolean checkSms(UserDto userDto);

    //登录
    boolean login(UserDto userDto);

    //修改密码
    String forgetpassword(UserDto userDto);
}
