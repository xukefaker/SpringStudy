package com.faker.day01.controller;

import com.faker.day01.DAO.*;
import com.faker.day01.pojo.ClassRecord;
import com.faker.day01.pojo.SubscribeRecord;
import com.faker.day01.pojo.UnsubscribeRecord;
import com.faker.day01.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SourceManageController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    SourceMapper sourceMapper;
    @Autowired
    SubscribeRecordMapper subscribeRecordMapper;
    @Autowired
    ClassRecordMapper classRecordMapper;
    @Autowired
    UnsubscribeRecordMapper unsubscribeRecordMapper;

    @RequestMapping("/user/getCurrentSubscribe")
    @ResponseBody
    public List<Map<String, String>> getCurrentSubscribe(String username){
        User user = userMapper.getUserByUserName(username);
        List<SubscribeRecord> subscribeRecords = subscribeRecordMapper.getSubscribeRecordByUserID(user.getId());
        List<Map<String, String>> results = new ArrayList<>();
        Map<String, String> temp;
        String url;
        String class2URL;
        ClassRecord classRecord;
        for (SubscribeRecord sb:subscribeRecords
             ) {
            temp = new HashMap<>();
            url = sourceMapper.getSourceByID(sb.getSourceID()).getUrl();
            classRecord = classRecordMapper.getClassRecordBySourceUserID(user.getId(), sb.getSourceID());
            if (classRecord == null){
                class2URL = "该源没有进行分类";
            }else {
                class2URL = classRecord.getSourceClass();
            }
            temp.put("url", url);
            temp.put("class",class2URL);
            temp.put("userID", String.valueOf(user.getId()));
            temp.put("sourceID", String.valueOf(sb.getSourceID()));

            results.add(new HashMap<>(temp));
        }
        return results;
    }

    @RequestMapping("/user/getDeletedSubscribe")
    @ResponseBody
    public List<Map<String, String>> getDeletedSubscribe(String username){
        List<Map<String, String>> results = new ArrayList<>();
        Map<String, String> temp;
        User user = userMapper.getUserByUserName(username);
        List<UnsubscribeRecord> unsubscribeRecords = unsubscribeRecordMapper.getUnsubscribeRecordByUserID(user.getId());
        String url;
        String deleteDate;
        for (UnsubscribeRecord usb:unsubscribeRecords
             ) {
            temp = new HashMap<>();
            url = sourceMapper.getSourceByID(usb.getSourceId()).getUrl();
            deleteDate = usb.getUnsubscribeTime();
            temp.put("url", url);
            temp.put("date", deleteDate);
            temp.put("userID", String.valueOf(usb.getUserId()));
            temp.put("sourceID", String.valueOf(usb.getSourceId()));

            results.add(temp);
        }
        return results;
    }



}
