package com.example.todolist.pojo.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String account;
    private String password;
    private String againpassword;
    private String phone;
    private String sms;
}
