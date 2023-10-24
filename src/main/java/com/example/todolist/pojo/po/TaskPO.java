package com.example.todolist.pojo.po;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskPO {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private String label;
}
