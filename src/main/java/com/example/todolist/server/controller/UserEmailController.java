package com.example.todolist.server.controller;

import com.example.todolist.common.constant.JwtClaimsConstant;
import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.result.JwtResult;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.pojo.dto.UserEmailDto;
import com.example.todolist.server.service.UserEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/userEmail")
@Slf4j
/**
 * 邮箱用户
 */
public class UserEmailController {

    @Autowired
    private UserEmailService userEmailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @GetMapping("/emailcode")
    public Result<String> sendEmailCode(@RequestParam String email) {
        log.info("发送验证码的账号为：{}",email);
        String code = userEmailService.sendVerificationCode(email);
        if (code != null) {
//            code.equals(null)
            return Result.success("发送验证码成功");
        }
        return Result.error("验证码发送失败");
    }

    /**
     * 判断是否有该用户
     *
     * @param email
     * @return
     */
    @GetMapping("/exit")
    public Result exitUserEmail(@RequestParam String email) {
        log.info("判断用户信息：{}",email);
        boolean user = userEmailService.exitByUser(email);
        if (user == true) {
            return Result.error("该用户已存在");
        }
        return Result.success("该用户不存在");
    }

    /**
     * 注册用户
     * @param userEmailDto
     * @return
     */
    @PostMapping("/signup")
    public Result signup(@RequestBody UserEmailDto userEmailDto) {
        log.info("用户信息：{}",userEmailDto);
        boolean add = userEmailService.addUserEmail(userEmailDto);
        if (add == true) {
            return Result.success("注册成功");
        }
        return Result.error("注册失败");
    }

    /**
     * 登录用户
     *
     * @param userEmailDto
     * @return
     */
    @PostMapping("/login")
    public JwtResult<String> login(@RequestBody UserEmailDto userEmailDto) {
        log.info("登录用户信息：{}",userEmailDto);
        //查询是否有该用户
        boolean user = userEmailService.loginUserEmail(userEmailDto);
        log.info("是否登录成功:{}", user);
        if (user) {
            //判断redis中是否有token
            if (stringRedisTemplate.hasKey(userEmailDto.getEmail())) {
                String exitToken = stringRedisTemplate.opsForValue().get(userEmailDto.getEmail());
                System.out.println("redis中的token值：" + exitToken);
                return JwtResult.success("登录成功", exitToken);
            }
            //无token，创建token
            Map<String,Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.User_Account, userEmailDto.getEmail());
            claims.put(JwtClaimsConstant.User_Password, userEmailDto.getPassword());
            String token =
                    JwtUtils.createJwt(jwtProperties.getToDoSecretKey(), jwtProperties.getToDoTtl(), claims);
            System.out.println("token值为：" + token);
            stringRedisTemplate.opsForValue().set(userEmailDto.getEmail(), token, 30, TimeUnit.DAYS);
            return JwtResult.success("登录成功", token);
        }
        return JwtResult.error("登录失败,请检查邮箱及密码");
    }

    /**
     * 退出登录
     * @param email
     * @return
     */
    @DeleteMapping("/logout")
    public Result logout(String email){
        boolean delete = Boolean.TRUE.equals(stringRedisTemplate.delete(email));
        if (delete){
            return Result.success("退出成功");
        }
        return Result.error("退出失败");
    }

    /**
     * 修改用户名或密码
     * @param userEmailDto
     * @return
     */
    @PutMapping("/edit")
    public Result editUser(@RequestBody UserEmailDto userEmailDto){
        boolean editUser = userEmailService.editUserByPass(userEmailDto);
        if (editUser){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    /**
     * 删除用户及其个人信息
     * @param email
     * @return
     */
    @DeleteMapping
    public Result deleteUser(@RequestParam String email){
        //先判断是否有该用户的token
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(email))){
            stringRedisTemplate.delete(email);
        }
        //删除该用户对应的账号及信息
        boolean b = userEmailService.deleteInformation(email);
        if (b){
            return Result.success("注销成功");
        }
        return Result.error("注销失败");
    }
}
