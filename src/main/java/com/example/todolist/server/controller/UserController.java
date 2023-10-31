package com.example.todolist.server.controller;

import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.pojo.dto.UserDto;
import com.example.todolist.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    //获取注册时验证码
    @PostMapping("/smscode")
    public Result message(@RequestBody UserDto userDto)throws Exception{
        String s = userService.sendSms(userDto.getPhone());
        return Result.success();
    }



    //注册
    @PostMapping("/signup")
    public Result signup(@RequestBody UserDto userDto){
        //先对比验证码是否校验成功
        boolean b = userService.checkSms(userDto);
        if (b==true){
            userService.signup(userDto);
            return Result.success(b);
        }
        return Result.error("注册失败");
    }

    //传输jwt令牌，超过令牌时间取消登录
    //登录用户
    @GetMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        boolean login = userService.login(userDto);
        //生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("account",userDto.getAccount());
        claims.put("password",userDto.getPassword());
        String token =
                JwtUtils.createJwt(jwtProperties.getToDoSecretKey(), jwtProperties.getToDoTtl(), claims);
        System.out.println(token);
        return Result.success(login,token);
    }

    //获取修改密码时的验证码
    @PostMapping("/smsbypassword")
    public Result smsbypassword(@RequestBody UserDto userDto)throws Exception{
        userService.sendSmsByPassword(userDto.getPhone());
        return Result.success();
    }

    //忘记密码可修改密码
    @PostMapping("/forgetpassword")
    public Result forgetpassword(@RequestBody UserDto userDto){
        boolean forgetpassword = userService.forgetpassword(userDto);
        return Result.success(forgetpassword);
    }
}
