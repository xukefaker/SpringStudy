package com.faker.day01.controller;

import com.faker.day01.DAO.UserMapper;
import com.faker.day01.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RegController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/user/reg")
    public String register(String username, String password, Model model){
        List<String> usernames = userMapper.getUsernames();
        if (usernames.contains(username)){
            model.addAttribute("regHint", "该用户名已被注册！");
            return "reg";
        }
        else{
            User user = new User(username, password);
            userMapper.addUser(user);
            model.addAttribute("regHint", "注册成功！请前往登陆界面");
            return "reg";
        }

    }
}
