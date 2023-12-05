package com.example.todolist.server.service;

import com.example.todolist.pojo.dto.InformationDTO;
import org.springframework.web.multipart.MultipartFile;

public interface InformationService {

    //添加个人信息
    boolean addInformation(InformationDTO informationDTO);

    //编辑个人信息
    boolean editInformation(InformationDTO informationDTO);

    //完善个人信息
    boolean prefectInformation(InformationDTO informationDTO);

    //查询个人信息
    InformationDTO queryInformation(String email);

    //上传头像
    String profilePhoto(String email, MultipartFile file);

    //回显头像
    String preview(String email);


}
