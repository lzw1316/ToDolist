package com.example.todolist.server.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload.path}")
    private String filePath;

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws IOException {
        //文件原始名称
        String originalFilename = file.getOriginalFilename();
        //截后缀
        String substring = originalFilename.substring(originalFilename.indexOf(".")-1);
        //判断目录是否存在
        File uploadFile=new File(filePath);
        if (!uploadFile.exists()){
            uploadFile.mkdirs();
        }
        //定义唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        //文件名称
        String finalPath=filePath+uuid+substring;
        //存储文件
        file.transferTo(new File(finalPath));
        String url="http://10.23.98.35/file/upload/";
        return url+finalPath;
    }
}
