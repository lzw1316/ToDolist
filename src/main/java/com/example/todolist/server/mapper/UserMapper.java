package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.UserDto;

public interface UserMapper {

    //添加用户
    int insertOneUser(UserDto userDto);

    //查询，某个用户
    UserDto selectByAccount(String account);

    //更改密码
    int updateByPassword(UserDto userDto);
}
