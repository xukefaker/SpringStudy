package com.faker.day01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnsubscribeRecord {
    private Integer id;
    private Integer userId;
    private Integer sourceId;
    private String unsubscribeTime;
    public UnsubscribeRecord(Integer userId, Integer sourceId, String unsubscribeTime) {
        this.userId = userId;
        this.sourceId = sourceId;
        this.unsubscribeTime = unsubscribeTime;
    }
}
