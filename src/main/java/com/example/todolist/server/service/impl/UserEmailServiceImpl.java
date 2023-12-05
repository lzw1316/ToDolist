package com.example.todolist.server.service.impl;

import com.example.todolist.common.exception.AccountException;
import com.example.todolist.common.exception.EmailNotRuleException;
import com.example.todolist.common.exception.PasswordException;
import com.example.todolist.common.exception.SmsNotRuleException;
import com.example.todolist.common.utils.EmaliUtils;
import com.example.todolist.pojo.dto.UserEmailDto;
import com.example.todolist.pojo.po.UserEmailPo;
import com.example.todolist.server.mapper.InformationMapper;
import com.example.todolist.server.mapper.UserEmailMapper;
import com.example.todolist.server.service.UserEmailService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class UserEmailServiceImpl implements UserEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserEmailMapper userEmailMapper;

    @Resource
    private InformationMapper informationMapper;

    @Autowired
    private EmaliUtils emaliUtils;

    //密码加盐
    private static final String pass_solf = "pass";


    //发送邮箱验证码
    @Override
    @CachePut(value = "EmailCode", key = "#toEmail")
    public String sendVerificationCode(String toEmail) {
        // 生成随机验证码
        Random random = new Random();
        int code = random.nextInt(1000, 9999);
        String verificationCode = String.valueOf(code);
        String email="([a-zA-Z0-9])+@([a-zA-Z0-9])+\\.([a-zA-Z0-9])+";
        if (toEmail.matches(email)) {
            // 发送邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("19830234860@163.com");
            message.setTo(toEmail);
            message.setSubject("注册验证码");
            message.setText("您的验证码是:" + verificationCode);
            javaMailSender.send(message);
            return verificationCode;
        }
        throw new EmailNotRuleException("邮箱格式错误");
    }

    //查询是否有该用户
    public boolean exitByUser(String email) {
        UserEmailDto dto = new UserEmailDto();
        dto.setEmail(email);
        UserEmailPo po = userEmailMapper.selectByEmail(dto);
        if (po != null) {
            return true;
        }
        return false;
    }

    @Override
    //注册用户
    public boolean addUserEmail(UserEmailDto userEmailDto) {
        UserEmailPo po = userEmailMapper.selectByEmail(userEmailDto);
        if (po != null){
            throw new AccountException("该账号已存在");
        }
        //判断邮箱密码格式是否正确
        String email = "([a-zA-Z0-9])+@([a-zA-Z0-9])+\\.([a-zA-Z0-9])+";
        String pass = "^(\\w{8,20}$)";
        if (userEmailDto.getEmail().matches(email)) {
            if (userEmailDto.getPassword().matches(pass)) {
                //判断验证码是否正确
                if (emaliUtils.getCode(userEmailDto.getEmail()).equals(userEmailDto.getCode())) {
                    //密码加盐
                    userEmailDto.setPassword(DigestUtils.md5DigestAsHex((pass_solf+userEmailDto.getPassword()).getBytes(StandardCharsets.UTF_8)));
                    //添加用户
                    int userEmail = userEmailMapper.insertUserEmail(userEmailDto);
                    if (userEmail==1){
                        return true;
                    }
                    return false;
                }
                throw new SmsNotRuleException("验证码错误");
            }
            throw new PasswordException("密码长度不满8位");
        }
        throw new EmailNotRuleException("邮箱不符合");
    }

    //用户登录
    @Override
    public boolean loginUserEmail(UserEmailDto userEmailDto) {
        //加盐用户传的密码
        userEmailDto.setPassword(DigestUtils.md5DigestAsHex((pass_solf+userEmailDto.getPassword()).getBytes(StandardCharsets.UTF_8)));
        //首先判断邮箱密码格式是否正确
        String email = "([a-zA-Z0-9])+@([a-zA-Z0-9])+\\.([a-zA-Z0-9])+";
        String pass = "^(\\w{8,20}$)";
        //查询是否有该用户
        UserEmailPo po = userEmailMapper.selectByEmail(userEmailDto);
        if (po==null){
            throw new AccountException("没有该用户");
        }
        if (po.getEmail().matches(email)&&po.getEmail().equals(userEmailDto.getEmail())){
            if (po.getPassword().equals(userEmailDto.getPassword())){
                return true;
            }
            throw new PasswordException("密码错误，请重新输入");
        }
        throw new AccountException("邮箱错误，请重新输入");
    }

    //修改用户名或密码
    @Override
    public boolean editUserByPass(UserEmailDto userEmailDto) {
        //先判断是否存在该用户
        UserEmailPo po = userEmailMapper.selectByEmail(userEmailDto);
        if (po!=null) {
            //对比验证码
            if (emaliUtils.getCode(userEmailDto.getEmail()).equals(userEmailDto.getCode())){
                String pass = "^(\\w{8,20}$)";
                if (userEmailDto.getPassword().matches(pass)){
                    userEmailDto.setPassword(DigestUtils.md5DigestAsHex((pass_solf+userEmailDto.getPassword()).getBytes(StandardCharsets.UTF_8)));
                    int i = userEmailMapper.updateUserEmail(userEmailDto);
                    if (i==1){
                        return true;
                    }
                    return false;
                }
                throw new PasswordException("密码应在8-20位内");
            }
            throw new SmsNotRuleException("验证码错误");
        }
        throw new AccountException("该用户不存在");
    }

    //删除用户名及其个人信息
    @Override
    public boolean deleteInformation(String email) {
        int i = informationMapper.deleteInformation(email);
        if (i==1){
            int user = userEmailMapper.deleteUserAndInformation(email);
            if (user==1){
                return true;
            }
            return false;
        }
        return false;
    }

}

