package com.faker.day01.controller;

import com.faker.day01.DAO.SubscribeRecordMapper;
import com.faker.day01.DAO.UnsubscribeRecordMapper;
import com.faker.day01.pojo.UnsubscribeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

@Controller
public class UnsubscribeController {

    @Autowired
    SubscribeRecordMapper subscribeRecordMapper;
    @Autowired
    UnsubscribeRecordMapper unsubscribeRecordMapper;

    @RequestMapping("/user/unsubscribe")
    @ResponseBody
    public void unsubscribe(String userID, String sourceID){
        UnsubscribeRecord unsubscribeRecord = new UnsubscribeRecord();
        unsubscribeRecord.setUserId(Integer.valueOf(userID));
        unsubscribeRecord.setSourceId(Integer.valueOf(sourceID));
        unsubscribeRecord.setUnsubscribeTime(new Date().toString());
        //不需要添加unsubscriberecord，已经写了触发器了
//        unsubscribeRecordMapper.addUnsubscribeRecord(unsubscribeRecord);
        subscribeRecordMapper.deleteSubscribeRecordByUserSourceID(Integer.valueOf(userID), Integer.valueOf(sourceID));

    }

    @RequestMapping("/user/unsubscribeForever")
    @ResponseBody
    public void unsubscribeForever(String userID, String sourceID){
        unsubscribeRecordMapper.deleteUnsubscribeRecordByUserSourceID(Integer.valueOf(userID), Integer.valueOf(sourceID));
    }

}
