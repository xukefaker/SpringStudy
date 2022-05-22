package com.faker.day01.DAO;

import com.faker.day01.pojo.ClassRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassRecordMapper {

    public void addClassRecord(ClassRecord classRecord);
    public ClassRecord getClassRecordByID(Integer id);
    public void deleteClassByID(@Param("id") Integer id);
    public List<ClassRecord> getClassRecords();
    public List<ClassRecord> getClassRecordsByUserID(Integer id);
    public List<ClassRecord> getClassRecordBySourceID(Integer id);
    public ClassRecord getClassRecordBySourceUserID(@Param("userID") Integer userID, @Param("sourceID") Integer sourceID);
    public void updateClassRecordByUserSourceID(@Param("userID") Integer userID, @Param("sourceID") Integer sourceID, @Param("className") String className);
}
