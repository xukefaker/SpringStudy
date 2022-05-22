package com.faker.day01.controller;

import com.faker.day01.DAO.ClassRecordMapper;
import com.faker.day01.pojo.ClassRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SourceClassController {

    @Autowired
    ClassRecordMapper classRecordMapper;

    @RequestMapping("/user/sourceClass")
    @ResponseBody
    public String sourceClass(String sourceClassName, String userID, String sourceID){
        ClassRecord classRecord = classRecordMapper.getClassRecordBySourceUserID(Integer.valueOf(userID), Integer.valueOf(sourceID));
        if (classRecord == null){
            classRecord = new ClassRecord();
            classRecord.setSourceClass(sourceClassName);
            classRecord.setUserID(Integer.valueOf(userID));
            classRecord.setSourceID(Integer.valueOf(sourceID));
            classRecordMapper.addClassRecord(classRecord);
            return "成功增加分类！";
        }else {
            classRecordMapper.updateClassRecordByUserSourceID(Integer.valueOf(userID), Integer.valueOf(sourceID), sourceClassName);
            return "成功修改分类！";
        }
    }
}
