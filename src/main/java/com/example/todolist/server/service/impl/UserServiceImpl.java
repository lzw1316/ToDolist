package com.example.todolist.server.service.impl;

import com.example.todolist.common.exception.AccountException;
import com.example.todolist.common.exception.PasswordException;
import com.example.todolist.common.properties.SendSmsProperties;
import com.example.todolist.common.utils.SendSmsUtils;
import com.example.todolist.pojo.dto.UserDto;
import com.example.todolist.server.mapper.UserMapper;
import com.example.todolist.server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private  SendSmsUtils sendSmsUtils;
    @Autowired
    private SendSmsProperties sendSmsProperties;
    @Resource
    private UserMapper userMapper;


    //获取验证码并且存放在redis里
    @Override
    @CachePut(value = "smsCode",key = "#tele")
    public String sendSms(String tele) throws Exception{
        String sms = sendSmsUtils.createSms(sendSmsProperties.getEndpoint(), sendSmsProperties.getAccessKeyId(),
                sendSmsProperties.getAccessKeySecret(), tele);
        return sms;
    }


    @Override
    //校验验证码
    public boolean checkSms(UserDto userDto) {
        String sms = sendSmsUtils.getSms(userDto.getPhone());
        System.out.println("sms="+sms);
        System.out.println(userDto.getSms());
        if (userDto.getSms().equals(sms)){
            return true;
        }
        return false;
    }


    //注册用户
    @Override
    public boolean signup(UserDto userDto) {
        if (userDto.getAccount()!=null&&userDto.getAccount()!=""){
            if (userDto.getPassword()!=null&&userDto.getPassword().equals(userDto.getAgainpassword())){
                userMapper.insertOneUser(userDto);
            }
            else { throw new PasswordException("密码格式错误");
            }
            return true;
        }
        throw new AccountException("账号格式错误");
    }

    //登录用户
    @Override
    public boolean login(UserDto userDto) {
        UserDto dto = userMapper.selectById(userDto.getId());
        if (userDto.getAccount().equals(dto.getAccount())&&userDto.getPassword().equals(dto.getPassword())){
            return true;
        }
        return false;
    }

//获取修改密码时所需的验证码
    @Override
    @CachePut(value = "smsByPassword",key = "#tele")
    public String sendSmsByPassword(String tele) throws Exception{
        String sms = sendSmsUtils.createSms(sendSmsProperties.getEndpoint(), sendSmsProperties.getAccessKeyId(),
                sendSmsProperties.getAccessKeySecret(), tele);
        return sms;
    }


    //修改密码
    public boolean forgetpassword(UserDto userDto){
        String sms = sendSmsUtils.getSmsByPassword(userDto.getPhone());
        if (userDto.getSms().equals(sms)&&userDto.getPassword().equals(userDto.getAgainpassword())){
            int i = userMapper.updateByPassword(userDto);
            if (i==1){
                return true;
            }
        }
        return false;
    }


}
