package com.example.todolist.server.service.impl;

import com.example.todolist.common.exception.BaseException;
import com.example.todolist.common.utils.MinioUtil;
import com.example.todolist.pojo.dto.InformationDTO;
import com.example.todolist.pojo.dto.UserEmailDto;
import com.example.todolist.pojo.po.InformationPo;
import com.example.todolist.pojo.po.UserEmailPo;
import com.example.todolist.server.config.MinioConfiguration;
import com.example.todolist.server.mapper.InformationMapper;
import com.example.todolist.server.mapper.UserEmailMapper;
import com.example.todolist.server.service.InformationService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InformationServiceImpl implements InformationService {

    @Resource
    private InformationMapper informationMapper;

    @Autowired
    private MinioConfiguration prop;

    @Autowired
    private MinioUtil minioUtil;

    @Resource
    private UserEmailMapper userEmailMapper;

    //添加个人信息
    @Override
    public boolean addInformation(InformationDTO informationDTO) {
        //查询用户对应的用户名
        UserEmailDto userEmailDto=new UserEmailDto();
        userEmailDto.setEmail(informationDTO.getUserEmail());
        UserEmailPo po = userEmailMapper.selectByEmail(userEmailDto);
        informationDTO.setUserUsername(po.getUsername());
        int i = informationMapper.insertInformation(informationDTO);
        if (i==1){
            return true;
        }
        throw new BaseException("添加用户信息失败");
    }

    //编辑个人信息
    @Override
    public boolean editInformation(InformationDTO informationDTO) {
//        if (informationDTO.getUserUsername()!=null){
//            UserEmailDto userEmailDto=new UserEmailDto();
//            userEmailDto.setUsername(informationDTO.getUserUsername());
//            userEmailMapper.updateUserEmail(userEmailDto);
//        }
        int i = informationMapper.updateInformation(informationDTO);
        if (i==1){
            return true;
        }
        return false;
    }

    //完善个人信息
    @Override
    public boolean prefectInformation(InformationDTO informationDTO) {
        //先查询用户是否有个人信息
        int existInformation = informationMapper.selectInformationByEmail(informationDTO.getUserEmail());
        if (existInformation==1){
            //用户数据库存在信息，可进行更改
            int update = informationMapper.updateInformation(informationDTO);
            if (update==1){
                return true;
            }
            throw new BaseException("更改用户信息失败");
        }
        else if (existInformation==0){
            //用户数据库不存在信息，进行添加操作
            int insert = informationMapper.insertInformation(informationDTO);
            if (insert==1){
                return true;
            }
            throw new BaseException("添加用户失败");
        }
        return false;
    }


    //查询个人信息
    @Override
    @Transactional
    public InformationDTO queryInformation(String email) {
        //先查询该用户是否有个人信息，没则报错
        int i = informationMapper.selectInformationByEmail(email);
        if (i!=1){
            throw new BaseException("该用户无个人信息，请先添加个人信息");
        }
        InformationPo informationPo = informationMapper.selectInformation(email);
        InformationDTO informationDTO=new InformationDTO();
        BeanUtils.copyProperties(informationPo,informationDTO);
        return informationDTO;
    }

    //上传头像
    @Override
    @Transactional
    public String profilePhoto(String email,MultipartFile file) {
        //先判断数据库中用户是否有头像，有则删除minio中的照片
        int informationByEmail = informationMapper.selectInformationByEmail(email);
        if (informationByEmail!=1){
            throw new BaseException("该用户无个人信息，请先完善个人信息");
        }
        InformationPo po = informationMapper.selectInformation(email);
        if (po.getImage()!=null){
            //获取数据库中的链接，且删除minio中的链接
            String image = informationMapper.selectPhoto(email);
            if (image!=null){
                boolean remove = minioUtil.remove(image);
                //上传头像
                String uploadImage = minioUtil.upload(file);
                int updatePhoto = informationMapper.updatePhoto(uploadImage, email);
                String preview = minioUtil.preview(uploadImage);
                if (remove&&updatePhoto==1){
                    return preview;
                }
                throw new BaseException("头像上传失败");
            }
        }
        //往数据库存储头像名称
        String uploadImage = minioUtil.upload(file);
        int addImage = informationMapper.updatePhoto(uploadImage, email);
        if (addImage==1){
            //向前端返回预览头像
            String preview = minioUtil.preview(uploadImage);
            return preview;
        }
        throw new BaseException("上传头像失败");
    }


    //回显头像
    @Override
    public String preview(String email) {
        //先判断数据库中是否存在该用户的头像
        int informationByEmail = informationMapper.selectInformationByEmail(email);
        if (informationByEmail!=1){
            throw new BaseException("该用户无个人信息，请先完善个人信息");
        }
        InformationPo po = informationMapper.selectInformation(email);
        if (po.getImage()==null){
            throw new BaseException("该用户不存在头像，请上传头像");
        }
        //获取数据库中图片路径
        String image = informationMapper.selectPhoto(email);
        String preview = minioUtil.preview(image);
        return preview;
    }


}
