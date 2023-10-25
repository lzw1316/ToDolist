package com.example.todolist.server.controller;

import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    //传输jwt令牌，超过令牌时间则会收回任务展示
    @GetMapping("")
    public Result queryAllTask() {
        //查询所有任务
        List<TaskDTO> dtos = taskService.AllTask();
        //生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("content", dtos.size());
        claims.put("account", "123456");
        String token =
                JwtUtils.createJwt(jwtProperties.getToDoSecretKey(), jwtProperties.getToDoTtl(), claims);
        System.out.println(token);
        //返回所有数据和token令牌
        return Result.success(dtos,token);

    }


    //查询某条内容
    @GetMapping("/{id}")
    @Cacheable(cacheNames ="addCache",key = "#id")
    public Result queryTaskByContent(@PathVariable Integer id) {
        log.info("查询的内容：{}", id);
        //查询此内容所在的整体数据
        TaskDTO taskDTO = taskService.selectByContent(id);
        return Result.success(taskDTO);
    }

    //新增任务，默认status为0,表示任务未完成
    @PostMapping("")
    @CachePut(cacheNames = "addCache",key = "#taskDTO")
    public Result addTask(@RequestBody TaskDTO taskDTO) {
        log.info("新增任务的信息：{}", taskDTO);
        boolean i = taskService.addTask(taskDTO);
        if (i == true) {
            return Result.success();
        }
        return Result.error("任务添加失败");
    }


    //根据任务内容编辑任务，修改任务，此时修改后日期应为修改时的时间
    @PutMapping("")
    public Result editTask(@RequestBody TaskDTO taskDTO) {
        log.info("进行编辑的内容：{}", taskDTO);
        boolean i = taskService.updateByTask(taskDTO);
        return Result.success();
    }


    //删除数据，可删除一条或者多条
    //可单击删除按钮删除此条内容，也可复选框后点击一键删除多条内容
    @DeleteMapping("/{ids}")
    @CacheEvict(cacheNames = "addCache" , allEntries = true)
    public Result removeByTasks(@PathVariable Integer[] ids) {
        log.info("删除数据的id：{}", ids);
        //将数组转化成集合
        List<Integer> list = new ArrayList<>(Arrays.asList(ids));
        boolean b = taskService.deleteByTasks(list);
        if (b == true) {
            return Result.success();
        }
        return Result.error("删除失败");
    }


    //分页查询，每次在页面展示10条数据
    // TODO: 2023/10/24 后期返回pageresult
    @GetMapping("/page")
    public Result queryByPage(@RequestBody PageDto pageDto) {
        Result result = taskService.queryByPage(pageDto);
        return result;
    }

    //拖拽排序
    @GetMapping("/sort")
    public Result queryBySerial(@RequestParam Integer startNumber,@RequestParam Integer endNumber) {
        List<TaskDTO> list = taskService.sortBySerial(startNumber, endNumber);
        return Result.success(list);
    }

    //根据用户传输的某一个信息进行过滤任务返回
    @GetMapping("/fifter")
    public Result queryByFifter(@RequestBody TaskDTO taskDTO){
        List<TaskDTO> list = taskService.filterByTask(taskDTO);
        return Result.success(list);
    }
}
