package com.faker.day01.controller;

import com.faker.day01.DAO.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ChangeInfoController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/user/quickChangeInfo")
    @ResponseBody
    public String quickChangeInfo(String originalUsername, String username, String password){
        List<String> usernames = userMapper.getUsernames();
        if (usernames.contains(username)){
            return "用户名已被注册！请重新选择用户名";
        }
        else {
            userMapper.quickChangeInfo(originalUsername, username, password);
            return "修改成功!";
        }
    }
    @RequestMapping("/user/changeInfo")
    @ResponseBody
    public String changeInfo(String originalUsername, String username, String password, String email, String birth, String phone){
        List<String> usernames = userMapper.getUsernames();
        if (usernames.contains(username))
            return "用户名已被注册！请重新选择用户名";
        else{
            userMapper.changeInfo(originalUsername, username, password, email, birth, phone);
            return "修改成功!";
        }
    }
}
