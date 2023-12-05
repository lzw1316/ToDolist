package com.example.todolist.pojo.po;

import com.example.todolist.pojo.domain.TaskParents;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskPO extends TaskParents implements Serializable {

    private String account;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Builder(toBuilder = true)
    public TaskPO(String id, String content, LocalDateTime updateTime, Integer serialNumber, Integer status, String account, String phone, LocalDateTime createTime) {
        super(id, content, updateTime, serialNumber, status);
        this.account = account;
        this.phone = phone;
        this.createTime = createTime;
    }
}
