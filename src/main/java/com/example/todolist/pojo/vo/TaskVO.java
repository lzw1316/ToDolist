package com.example.todolist.pojo.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskVO implements Serializable {
    private Integer id;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;
    private String label;
    private Integer serialNumber;
    private Integer status;
}
