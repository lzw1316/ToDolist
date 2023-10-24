package com.example.todolist.server.controller;

import com.example.todolist.common.constant.StatusConstant;
import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskByStatusDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.vo.JwtByContentVo;
import com.example.todolist.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtProperties jwtProperties;

    //所有任务展示给前端
    @GetMapping("")
    public Result queryAllTask(){
        List<TaskDTO> dtos = taskService.AllTask();
        return Result.success(dtos);
    }

    //新增任务，默认status为0,表示任务未完成
    @PostMapping("")
    public Result addTask(@RequestBody TaskDTO taskDTO){
        log.info("新增任务的信息：{}",taskDTO);
        boolean i = taskService.addTask(taskDTO);
        if (i==true){
            return Result.success();
        }
        return Result.error("任务添加失败");
    }

    //查询某条内容
    //先将查询的内容对应的任务返回给客户端
    //根据客户端传来的内容传输jwt令牌，超过令牌时间则会收回任务展示
    @GetMapping("/{content}")
    public Result queryTaskByContent(@PathVariable String content){
        log.info("查询的内容：{}",content);
        //查询此内容所在的整体数据
        TaskDTO taskDTO = taskService.selectByContent(content);

        //传输内容成功后，传输jwt令牌
        Map<String,Object> claims=new HashMap<>();
        claims.put("content",content);
        claims.put("account","123456");
        String token =
                JwtUtils.createJwt(jwtProperties.getToDoSecretKey(), jwtProperties.getToDoTtl(), claims);
        System.out.println(token);
        JwtByContentVo jwtByContentVo=JwtByContentVo.builder()
                .id(taskDTO.getId())
                .content(taskDTO.getContent())
                .createTime(taskDTO.getCreateTime())
                .token(token).build();

        return Result.success(jwtByContentVo);
    }

    //根据任务内容编辑任务，修改任务，此时修改后日期应为修改时的时间
    @PutMapping("")
    public Result editTask(TaskByStatusDto taskByStatusDto){
        log.info("进行编辑的内容：{}",taskByStatusDto);
        boolean i = taskService.updateByTask(taskByStatusDto);
        return Result.success();
    }


    //删除数据，可删除一条或者多条
    //可单击删除按钮删除此条内容，也可复选框后点击一键删除多条内容
    @DeleteMapping("/{ids}")
    public Result removeByTasks(@PathVariable Integer[] ids){
        log.info("删除数据的id：{}",ids);
        //将数组转化成集合
        List<Integer> list=new ArrayList<>(Arrays.asList(ids));
        boolean b =taskService.deleteByTasks(list);
        if (b==true){
            return Result.success();
        }
        return Result.error("删除失败");
    }

    //标志任务完成，通过传输status=1表示完成，可以动态输入1，方便后期维护
    @GetMapping("/status/{content}")
    public Result successByTask(@PathVariable String content) {
        log.info("测试任务content：{}",content);
        //查询完成的是哪个任务
        TaskDTO dto = taskService.selectByContent(content);
        //传输status=1
        int status = taskService.statusToSuccess(dto);
        if (status == StatusConstant.Status_success) {
            return Result.success();
        }
        return Result.error("传输不成功");
    }

    //根据status进行任务分类，0分配成未完成，1则为完成
    // TODO: 2023/10/23  后期需要设置status常量，方便后期修改
    @GetMapping("/status/success")
    public Result sortByStatus(){
//        log.info("查询的状态：{}",success);
        Integer success=0;
        List<TaskDTO> dtos = taskService.selectByStatus(success);
        return Result.success(dtos);
    }

    //分页查询，每次在页面展示10条数据
    // TODO: 2023/10/24 后期返回pageresult
    @GetMapping("/page")
    public Result queryByPage(PageDto pageDto){
        Result result = taskService.queryByPage(pageDto);
        return result;
    }

    //根据序号排序
    @GetMapping("/sort")
    public Result queryBySerial(Integer startNumber,Integer endNumber){
        List<TaskDTO> list = taskService.sortBySerial(startNumber, endNumber);
        return Result.success(list);
    }
}
