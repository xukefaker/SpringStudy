package com.faker.day01;

import com.faker.day01.DAO.SourceMapper;
import com.faker.day01.controller.SubscribeController;
import com.faker.day01.pojo.Source;
import com.faker.day01.utils.IndexesOfSubstring;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes =Day01Application.class)
@RunWith(SpringRunner.class)
class Day01ApplicationTests {

    @Autowired
    SourceMapper sourceMapper;

    @Test
    void contextLoads() throws Exception {

    }

}
