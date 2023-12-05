package com.example.todolist.server.service.impl;

import com.example.todolist.common.exception.AccountException;
import com.example.todolist.common.exception.PasswordException;
import com.example.todolist.common.exception.SmsNotRuleException;
import com.example.todolist.common.properties.SendSmsProperties;
import com.example.todolist.common.utils.SendSmsUtils;
import com.example.todolist.pojo.dto.UserDto;
import com.example.todolist.server.mapper.UserMapper;
import com.example.todolist.server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private  SendSmsUtils sendSmsUtils;
    @Autowired
    private SendSmsProperties sendSmsProperties;

    @Resource
    private UserMapper userMapper;

    //密码加盐
    private static final String pass_salt="LIST";


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
        //获取缓存中的验证码
        String sms = sendSmsUtils.getSms(userDto.getPhone());
        System.out.println("获取缓存中的验证码sms="+sms);
        System.out.println("用户传输的验证码为："+userDto.getSms());
        if (userDto.getSms().equals(sms)){
            return true;
        }
        throw new SmsNotRuleException("验证码校验失败");
    }


    //注册用户
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean signup(UserDto userDto) {
        if (userDto.getAccount()!=null&&userDto.getAccount()!=""){
            //判断该账号是否存在
            UserDto account = userMapper.selectByAccount(userDto.getAccount());
            if (account==null){
                if (userDto.getPassword()!=null&&userDto.getPassword().equals(userDto.getAgainpassword())){
                    //进行md5加密
                    userDto.setPassword(DigestUtils.md5DigestAsHex((pass_salt+userDto.getPassword()).getBytes(StandardCharsets.UTF_8)));
                    userMapper.insertOneUser(userDto);
                    return true;
                }
                else { throw new PasswordException("密码格式错误");
                }
            }
            throw new AccountException("账号已存在");
        }
        throw new AccountException("账号格式错误");
    }

    //登录用户
    @Override
    public boolean login(UserDto userDto) {
        String account=userDto.getAccount();
        String password=userDto.getPassword();
        UserDto dto = userMapper.selectByAccount(account);
        if (dto==null){
            throw new AccountException("账号未找到");
        }
        //加密，对比密码是否一样
        password=DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (password.equals(dto.getPassword())&&account.equals(dto.getAccount())){
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
    public String forgetpassword(UserDto userDto){
        String password=DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes(StandardCharsets.UTF_8));
        String anginpassword=
                DigestUtils.md5DigestAsHex(userDto.getAgainpassword().getBytes(StandardCharsets.UTF_8));
        userDto.setPassword(password);
        userDto.setAgainpassword(anginpassword);
        //获取验证码
        String sms = sendSmsUtils.getSmsByPassword(userDto.getPhone());
        //校验验证码及密码
        if (userDto.getSms().equals(sms)&&password.equals(anginpassword)){
            int i = userMapper.updateByPassword(userDto);
            if (i==1){
                return "修改成功";
            }
        }
        return "修改密码失败";
    }


}
