package com.example.todolist.server.controller;

import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.MinioUtil;
import com.example.todolist.server.config.MinioConfiguration;
import io.minio.messages.Bucket;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "product/file")
/**
 * minio
 */
public class MinioController {


    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioConfiguration prop;

    /**
     * 文件上传返回url
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return Result.success((prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName));
        }
        return Result.error("文件上传失败");
    }

    /**
     * 图片视频预览
     * @param fileName
     * @return
     */
    @GetMapping("/preview")
    public Result preview(@RequestParam("fileName") String fileName) {
        return Result.success(minioUtil.preview(fileName));
    }

    /**
     * 根据url地址删除文件
     * @param url
     * @return
     */
    @PostMapping("/delete")
    public Result remove(String url) {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName()+"/") + prop.getBucketName().length()+1);
        minioUtil.remove(objName);
        return Result.success(objName);
    }

    /**
     * 文件下载
     * @param fileName
     * @param res
     * @return
     */
    @GetMapping("/download")
    public Result download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minioUtil.download(fileName,res);
        return Result.success();
    }


    /**
     * 查看存储bucket是否存在
     * @param bucketName
     * @return
     */
    @GetMapping("/bucketExists")
    public Result bucketExists(@RequestParam("bucketName") String bucketName) {
        return Result.success("bucketName:"+minioUtil.bucketExists(bucketName));
    }

    /**
     * 创建存储bucket
     * @param bucketName
     * @return
     */
    @GetMapping("/makeBucket")
    public Result makeBucket(String bucketName) {
        return Result.success("bucketName:"+minioUtil.makeBucket(bucketName));
    }

    /**
     * 删除存储bucket
     * @param bucketName
     * @return
     */
    @GetMapping("/removeBucket")
    public Result removeBucket(String bucketName) {
        return Result.success("bucketName:"+minioUtil.removeBucket(bucketName));
    }

    /**
     *"获取全部bucket
     * @return
     */
    @GetMapping("/getAllBuckets")
    public Result getAllBuckets() {
        List<Bucket> allBuckets = minioUtil.getAllBuckets();
        return Result.success("allBuckets:"+allBuckets);
    }



}
