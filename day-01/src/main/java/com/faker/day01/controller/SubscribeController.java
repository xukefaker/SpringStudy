package com.faker.day01.controller;

import com.faker.day01.DAO.*;
import com.faker.day01.pojo.ClassRecord;
import com.faker.day01.pojo.Source;
import com.faker.day01.pojo.SubscribeRecord;
import com.faker.day01.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SubscribeController {
     @Autowired
     private SourceMapper sourceMapper;
     @Autowired
     private SubscribeRecordMapper subscribeRecordMapper;
     @Autowired
     private UserMapper userMapper;
     @Autowired
     private UnsubscribeRecordMapper unsubscribeRecordMapper;
     @Autowired
     private ClassRecordMapper classRecordMapper;

    @RequestMapping("/user/addSubscribe")
    @ResponseBody
    //将该订阅源添加至用户订阅的列表中
    public String addSubscribe(String url, String username){
        //由于固定前缀已经带了/，所以这里要把开头的/去掉
        if (url.charAt(0) == '/')
            url = url.substring(1);
        List<String> urls = sourceMapper.getUrls();
        Source source;
        User user = userMapper.getUserByUserName(username);
        if (urls.contains(url)){
            source = sourceMapper.getSourceByURL(url);
            //判断用户是否已经订阅该内容源
            List<SubscribeRecord> userSubscribeRecords = subscribeRecordMapper.getSubscribeRecordByUserID(user.getId());
            List<String> userUrls = new ArrayList<>();
            for (SubscribeRecord sb: userSubscribeRecords)
                userUrls.add(sourceMapper.getSourceByID(sb.getSourceID()).getUrl());
            if (userUrls.contains(url))
                return "您已经订阅该内容源！";
            //如果曾经删除过这个订阅源，还要把其从unsubscriberecord中移除,已经通过触发器实现了
        }else {
            //source表里面没有这个url，加进去
            source = new Source();
            source.setUrl(url);
            source.setUpdateTime(new Date().toString());
            sourceMapper.addSource(source);
        }

        Integer sourceID = source.getId();

        SubscribeRecord subscribeRecord = new SubscribeRecord();
        subscribeRecord.setSourceID(sourceID);
        subscribeRecord.setUserID(user.getId());
        subscribeRecord.setSubscribeTime(new Date().toString());
        subscribeRecordMapper.addSubscribeRecord(subscribeRecord);

        return "订阅成功！";
    }

    @RequestMapping("/user/resubscribe")
    @ResponseBody
    //用户从订阅源回收站选择订阅源重新订阅
    public Map<String, String> resubscribe(String userID, String sourceID){
        SubscribeRecord subscribeRecord = new SubscribeRecord();
        subscribeRecord.setUserID(Integer.valueOf(userID));
        subscribeRecord.setSourceID(Integer.valueOf(sourceID));
        subscribeRecord.setSubscribeTime(new Date().toString());
        subscribeRecordMapper.addSubscribeRecord(subscribeRecord);
        unsubscribeRecordMapper.deleteUnsubscribeRecordByUserSourceID(Integer.valueOf(userID), Integer.valueOf(sourceID));

        Map<String, String> result = new HashMap<>();
        String class2URL;

        ClassRecord classRecord = classRecordMapper.getClassRecordBySourceUserID(Integer.valueOf(userID), Integer.valueOf(sourceID));
        if (classRecord == null)
            class2URL = "该源没有进行分类";
        else
            class2URL = classRecord.getSourceClass();
        result.put("class",class2URL);
        result.put("url", sourceMapper.getSourceByID(Integer.valueOf(sourceID)).getUrl());
        result.put("userID", userID);
        result.put("sourceID", sourceID);
        return result;

    }





}

