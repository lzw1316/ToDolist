package com.example.todolist.pojo.dto;

import com.example.todolist.pojo.domain.TaskParents;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class  TaskDTO extends TaskParents implements Serializable {
    private String account;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Builder(toBuilder = true)
    public TaskDTO(Integer id, String content, LocalDateTime updateTime, Integer serialNumber, Integer status, String account, LocalDateTime createTime) {
        super(id, content, updateTime, serialNumber, status);
        this.account = account;
        this.createTime = createTime;
    }
}
