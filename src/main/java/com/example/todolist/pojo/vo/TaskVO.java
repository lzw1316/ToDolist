package com.example.todolist.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskVO {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private String label;
    private Integer serialNumber;
}
