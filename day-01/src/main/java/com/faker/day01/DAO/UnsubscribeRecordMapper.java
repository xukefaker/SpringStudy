package com.faker.day01.DAO;

import com.faker.day01.pojo.SubscribeRecord;
import com.faker.day01.pojo.UnsubscribeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UnsubscribeRecordMapper {
    public void addUnsubscribeRecord(UnsubscribeRecord unsubscribeRecord);
    public UnsubscribeRecord getUnsubscribeRecordByID(Integer id);
    public List<UnsubscribeRecord> getUnsubscribeRecordByUserID(Integer id);
    public List<UnsubscribeRecord> getUnsubscribeRecords();
    public void deleteUnsubscribeRecordByUserSourceID(@Param("userID") Integer userID, @Param("sourceID") Integer sourceID);

}
