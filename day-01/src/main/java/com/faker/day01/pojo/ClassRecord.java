package com.faker.day01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//用户对订阅源的分类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRecord {
    private Integer id;
    private Integer userID;
    private Integer sourceID;
    private String sourceClass;

}
