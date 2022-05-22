package com.faker.day01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String wechatID;
    private Date birthday;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }


}
