package com.example.todolist.server.mapper;

import com.example.todolist.pojo.dto.InformationDTO;
import com.example.todolist.pojo.po.InformationPo;

public interface InformationMapper {

    //添加用户信息
    int insertInformation(InformationDTO informationDTO);

    //修改个人信息，用户名修改同时修改
    int updateInformation(InformationDTO informationDTO);

    //查询是否有该用户的个人信息
    int selectInformationByEmail(String email);

    //查询个人信息
    InformationPo selectInformation(String email);

    //注销个人信息
    int deleteInformation(String email);

    //查询头像链接
    String selectPhoto(String email);

    //更新头像
    int updatePhoto(String image,String email);


}