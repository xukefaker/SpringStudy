package com.faker.day01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id;
    private int sourceID;
    private String pubDate;
    private String title;
    private String description;
    private String link;
    private String platform;
}
