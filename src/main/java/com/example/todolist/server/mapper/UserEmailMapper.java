package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.UserEmailDto;
import com.example.todolist.pojo.po.UserEmailPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserEmailMapper {

    //查找是否存在该用户
    UserEmailPo selectByEmail(UserEmailDto userEmailDto);

    //查询所有用户
    @Select("select * from useremail")
    List<UserEmailPo> ALL();

    //添加用户
    @Insert("insert into useremail(username,email,password) values (#{username},#{email},#{password})")
    int insertUserEmail(UserEmailDto userEmailDto);

    //修改用户名或密码
    int updateUserEmail(UserEmailDto userEmailDto);

    @Delete("delete from useremail where email=#{email}")
    //删除用户及其信息
    int deleteUserAndInformation(String email);
}
