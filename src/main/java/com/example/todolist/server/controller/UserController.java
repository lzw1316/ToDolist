package com.example.todolist.server.controller;

import com.example.todolist.common.constant.JwtClaimsConstant;
import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.result.JwtResult;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.pojo.dto.UserDto;
import com.example.todolist.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")

@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取注册时验证码
     * @param tele
     * @return
     * @throws Exception
     */
    @GetMapping("/smscode")
    public Result message(@RequestParam String tele)throws Exception{
        log.info("用于注册的手机号码为：{}",tele);
        String sms = userService.sendSms(tele);
        return Result.success(sms);
    }


    /**
     *注册账号
     * @param userDto
     * @return
     */
    @PostMapping("/signup")
    public Result signup(@RequestBody UserDto userDto){
        //对比密码是否一致
        boolean signup = userService.signup(userDto);
        if (signup==true){
            //对比验证码是否校验成功
            boolean b = userService.checkSms(userDto);
            if (b==true){return Result.success("注册成功");}
            else {return Result.error("验证码校验错误");}
        }
        return Result.error("注册失败");
    }

    //传输jwt令牌，超过令牌时间取消登录
    /**
     * 登录用户
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public JwtResult login(@RequestBody UserDto userDto){
        log.info("登录的用户信息为：{}",userDto);
        boolean login = userService.login(userDto);
        log.info("是否登录成功：{}",login);
        //判断是否存在token
        if (redisTemplate.hasKey(userDto.getAccount())==true){
            String exitToken =(String) redisTemplate.opsForValue().get(userDto.getAccount());
            System.out.println("token值为："+exitToken);
            if (login==true){
                return JwtResult.success("登录成功",exitToken);
            }
            return JwtResult.error("登录失败，账号或密码错误");
        }
        //生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.User_Account,userDto.getAccount());
        claims.put(JwtClaimsConstant.User_Password,userDto.getPassword());
        String token =
                JwtUtils.createJwt(jwtProperties.getToDoSecretKey(), jwtProperties.getToDoTtl(), claims);
        System.out.println("token值为："+token);
        redisTemplate.opsForValue().set(userDto.getAccount(),token,1, TimeUnit.DAYS);
        if (login==true){
            return JwtResult.success("登陆成功",token);
        }
        return JwtResult.error("登录失败，账号或密码错误");
    }

    /**
     * 获取修改密码时的验证码
     * @param
     * @return
     * @throws Exception
     */
    //弃用
    @PostMapping("/smsbypassword")
    public Result smsbypassword(@RequestParam String tele)throws Exception{
        String sms = userService.sendSmsByPassword(tele);
        return Result.success(sms);
    }

    /**
     * 修改密码
     * @param userDto
     * @return
     */
    //弃用
    @PutMapping("/forgetpassword")
    public Result forgetpassword(@RequestBody UserDto userDto){
        String forgetpassword = userService.forgetpassword(userDto);
        return Result.success(forgetpassword);
    }
}
