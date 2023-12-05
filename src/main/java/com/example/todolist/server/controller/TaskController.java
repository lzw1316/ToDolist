package com.example.todolist.server.controller;

import com.example.todolist.common.result.PageResult;
import com.example.todolist.common.result.Result;
import com.example.todolist.common.utils.BaseUtils;
import com.example.todolist.pojo.dto.PageDto;
import com.example.todolist.pojo.dto.TaskDTO;
import com.example.todolist.pojo.vo.TaskContentVo;
import com.example.todolist.pojo.vo.TaskVO;
import com.example.todolist.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 任务
 */
@RestController
@RequestMapping("/task")

@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;


    /**
     * 展示所有用户
     * @return
     */
    @GetMapping
    public Result queryAllTask() {
        List<TaskVO> vos=new ArrayList<>();
        //查询所有任务
        List<TaskDTO> dtos = taskService.AllTask(BaseUtils.getCurrentAccount());
        for (TaskDTO dto : dtos) {
            TaskVO vo=new TaskVO();
            BeanUtils.copyProperties(dto,vo);
            vos.add(vo);
        }
        //返回所有数据
        return Result.success(vos);
    }


    /**
     * 根据内容查询某条或多条内容
     * @param content
     * @return
     */
    @GetMapping("/content")
    public Result queryTaskByContent(String content) {
        log.info("查询的内容：{}", content);
        List<TaskContentVo> taskContentVos=new ArrayList<>();
        //查询此内容所在的整体数据
        List<TaskDTO> list = taskService.selectByContent(content);
        System.out.println(list);
        for (TaskDTO dto : list) {
            TaskContentVo vo=new TaskContentVo();
            BeanUtils.copyProperties(dto,vo);
            taskContentVos.add(vo);
        }
        return Result.success(taskContentVos);
    }

    /**
     * 新增任务
     * @param taskDTO
     * @return
     */
    @PostMapping
    public Result addTask(@RequestBody TaskDTO taskDTO) {
        log.info("新增任务的信息：{}", taskDTO);
        boolean b= taskService.addEmailTask(taskDTO);
        if (b==true){
            return Result.success("添加任务成功");
        }
       return Result.error("任务添加失败");
    }

    /**
     * 编辑任务
     * @param taskDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result editTask(@RequestBody TaskDTO taskDTO) {
        log.info("进行编辑的内容：{}", taskDTO);
        taskDTO.setAccount(BaseUtils.getCurrentAccount());
        boolean i = taskService.updateByTask(taskDTO);
        if (i == true) {
            return Result.success("编辑任务成功");
        }
        return Result.error("任务编辑失败");
    }

    //删除数据，可删除一条或者多条
    /**
     * 删除任务
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result removeByTasks(@PathVariable String[] ids) {
        log.info("删除数据的id：{}", ids);
        //将数组转化成集合
        List<String> list = new ArrayList<>(Arrays.asList(ids));
        boolean b = taskService.deleteByTasks(list);
        if (b == true) {
            return Result.success("删除任务成功");
        }
        return Result.error("删除任务失败");
    }

    //分页查询，每次在页面展示10条数据
    /**
     * 分页查询
     * @param pageDto
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> queryByPage(PageDto pageDto) {
        log.info("分页的数据：{}",pageDto);
        pageDto.setAccount(BaseUtils.getCurrentAccount());
        PageResult result = taskService.queryByPage(pageDto);
        return Result.success(result);
    }


    //拖拽排序
    /**
     * 拖拽排序
     * @param startNumber
     * @param endNumber
     * @return
     */
    @GetMapping("/sort")
    public Result queryBySerial(@RequestParam Integer startNumber, @RequestParam Integer endNumber) {
        log.info("拖拽任务开始执行");
        System.out.println("需要重新排序的id为："+startNumber+"和"+endNumber);
        List<TaskVO> vos=new ArrayList<>();
        for (TaskDTO dto : taskService.sortBySerial(startNumber, endNumber)) {
            TaskVO vo=new TaskVO();
            BeanUtils.copyProperties(dto,vo);
            vos.add(vo);
        }
        return Result.success(vos);
    }

    //根据用户传输的某一个信息进行过滤任务返回
    //弃用
    @GetMapping("/fifter")
    public Result queryByFifter(@RequestBody TaskDTO taskDTO) {
        log.info("过滤的任务：{}",taskDTO);
        taskDTO.setAccount(BaseUtils.getCurrentAccount());
        List<TaskDTO> list = taskService.filterByTask(taskDTO);
        return Result.success(list);
    }

}
