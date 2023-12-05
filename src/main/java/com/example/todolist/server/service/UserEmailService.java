package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.UserEmailDto;

public interface UserEmailService {

    //发送邮箱验证码
    String sendVerificationCode(String toEmail);

    //查询是否有该用户
    boolean exitByUser(String email);

    //注册用户
    boolean addUserEmail(UserEmailDto userEmailDto);

    //登录
    boolean loginUserEmail(UserEmailDto userEmailDto);

    //修改密码
    boolean editUserByPass(UserEmailDto userEmailDto);

    //删除个人信息
    boolean deleteInformation(String email);
}
