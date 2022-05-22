package com.faker.day01.DAO;

import com.faker.day01.pojo.SubscribeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubscribeRecordMapper {

    //三个首要方法+根据外键ID获取类的方法
    public void addSubscribeRecord(SubscribeRecord subscribeRecord);
    public SubscribeRecord getSubscribeRecordByID(Integer id);
    public List<SubscribeRecord> getSubscribeRecords();
    public List<SubscribeRecord> getSubscribeRecordByUserID(Integer id);
    public List<SubscribeRecord> getSubscribeRecordBySourceID(Integer id);
    public void deleteSubscribeRecordByUserSourceID(@Param("userID") Integer userID, @Param("sourceID") Integer sourceID);
}
