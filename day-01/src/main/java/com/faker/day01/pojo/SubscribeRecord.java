package com.faker.day01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class SubscribeRecord {
    private Integer id;
    private Integer userID;
    private Integer sourceID;
    private String subscribeTime;

    public SubscribeRecord(Integer id, Integer userID, Integer sourceID, String subscribeTime) {
        this.id = id;
        this.userID = userID;
        this.sourceID = sourceID;
        this.subscribeTime = subscribeTime;
    }

    public SubscribeRecord() {
    }

    @Override
    public String toString() {
        return "SubscribeRecord{" +
                "id=" + id +
                ", userID=" + userID +
                ", sourceID=" + sourceID +
                ", subscribeTime=" + subscribeTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getSourceID() {
        return sourceID;
    }

    public void setSourceID(Integer sourceID) {
        this.sourceID = sourceID;
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
}
