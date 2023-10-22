package com.example.todolist.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
}
