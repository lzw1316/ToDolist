package com.example.todolist.server.controller;

import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.BaseUtils;
import com.example.todolist.common.utils.MinioUtil;
import com.example.todolist.pojo.dto.InformationDTO;
import com.example.todolist.pojo.vo.InformationVo;
import com.example.todolist.server.service.InformationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private MinioUtil minioUtil;

//    /**
//     * 添加个人信息
//     * @param informationDTO
//     * @return
//     */
//    @PostMapping("/add")
//    public Result<String> addInformation(@RequestBody InformationDTO informationDTO){
//        //存储email
//        informationDTO.setUserEmail(BaseUtils.getCurrentAccount());
//        boolean b = informationService.addInformation(informationDTO);
//        if (b){
//            return Result.success("添加个人信息成功");
//        }
//        return Result.error("添加失败");
//    }

    /**
     * 更改个人信息
     * @param informationDTO
     * @return
     */
//    @PutMapping("/edit")
//    public Result editInformation(@RequestBody InformationDTO informationDTO){
//        informationDTO.setUserEmail(BaseUtils.getCurrentAccount());
//        boolean b = informationService.editInformation(informationDTO);
//        if (b){
//           return  Result.success("修改成功");
//        }
//        return Result.error("修改失败");
//    }

    /**
     * 完善个人信息
     */
    @PutMapping("/perfect")
    public Result perfectInformation(@RequestBody InformationDTO informationDTO){
        informationDTO.setUserEmail(BaseUtils.getCurrentAccount());
        boolean perfect = informationService.prefectInformation(informationDTO);
        if (perfect){
            return Result.success("完善用户信息成功");
        }
        return Result.error("完善用户信息失败");
    }

    /**
     * 查询个人信息
     * @return
     */
    @GetMapping
    public Result queryInformation(){
        String email=BaseUtils.getCurrentAccount();
        InformationDTO informationDTO = informationService.queryInformation(email);
        if (informationDTO!=null){
            InformationVo informationVo=new InformationVo();
            BeanUtils.copyProperties(informationDTO,informationVo);
            return Result.success(informationVo);
        }
       return Result.error("没有该用户");
    }

    /**
     * 上传头像
     * @param file
     * @return
     */
    @PostMapping("/uploadPhoto")
    public Result uploadPhoto(@RequestParam("file") MultipartFile file){
        String email=BaseUtils.getCurrentAccount();
        String photo = informationService.profilePhoto(email,file);
        if (photo!=null){
            return Result.success(photo);
        }
        return Result.error("上传头像失败");
    }

    /**
     * 回显头像
     * @return
     */
    @GetMapping("/preview")
    public Result previewPhoto() {
        String preview = informationService.preview(BaseUtils.getCurrentAccount());
        if (preview!=null){
            return Result.success(preview);
        }
        return Result.error("回显头像失败");
    }
}
