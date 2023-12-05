package com.example.todolist.pojo.vo;

import com.example.todolist.pojo.domain.InformationParents;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InformationVo extends InformationParents {

    @Builder(toBuilder = true)
    public InformationVo(String userUsername, String sex, Date birthday, String phone, String job, String school, String cityLocation, String homeland, String userEmail) {
        super(userUsername, sex, birthday, phone, job, school, cityLocation, homeland, userEmail);
    }
}