package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    //添加用户
    @Insert("insert into user(account,password,phone) values(#{account},#{password},#{phone})")
    int insertOneUser(UserDto userDto);


    //查询，某个用户
    @Select("select password from user where account =#{account}")
    UserDto selectByAccount(String account);

    //更改密码
    int updateByPassword(UserDto userDto);
}
