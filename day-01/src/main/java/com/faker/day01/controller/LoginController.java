package com.faker.day01.controller;

import com.faker.day01.DAO.ArticleMapper;
import com.faker.day01.DAO.SourceMapper;
import com.faker.day01.DAO.SubscribeRecordMapper;
import com.faker.day01.DAO.UserMapper;
import com.faker.day01.pojo.SubscribeRecord;
import com.faker.day01.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class LoginController {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SubscribeRecordMapper subscribeRecordMapper;
    @Autowired
    private SourceMapper sourceMapper;

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        List<String> usernames = userMapper.getUsernames();
        if (usernames.contains(username)){
            User user = userMapper.getUserByUserName(username);
            if (password.equals(user.getPassword())) {

                model.addAttribute("username", user.getUsername());
                if (user.getPhone() != null)
                    model.addAttribute("phone", user.getPhone());
                if (user.getEmail() != null)
                    model.addAttribute("email", user.getEmail());
                if (user.getBirthday() != null)
                    model.addAttribute("birth", new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()));
                return "dashboard";
            }
            else {
                model.addAttribute("msg", "密码错误！");
                return "index";
            }
        }else{
            model.addAttribute("msg", "用户不存在！");
            return "index";
        }


    }

}
