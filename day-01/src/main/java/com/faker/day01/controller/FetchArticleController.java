package com.faker.day01.controller;

import com.faker.day01.DAO.ArticleMapper;
import com.faker.day01.DAO.SubscribeRecordMapper;
import com.faker.day01.DAO.UserMapper;
import com.faker.day01.pojo.Article;
import com.faker.day01.pojo.SubscribeRecord;
import com.faker.day01.utils.RamdonNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FetchArticleController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SubscribeRecordMapper subscribeRecordMapper;
    @Autowired
    private ArticleMapper articleMapper;


    @ResponseBody
    @RequestMapping("/user/fetchArticles")
    //用户主动请求更新前端文章
    public List<Article> fetchArticles(String username){
        Integer userID = userMapper.getUserByUserName(username).getId();
        List<SubscribeRecord> subscribeRecords = subscribeRecordMapper.getSubscribeRecordByUserID(userID);
        List<Article> targetArticles = new ArrayList<>();
        List<Integer> targetArticleIDs = new ArrayList<>();

        for (SubscribeRecord sb: subscribeRecords
             ) {
            targetArticleIDs.addAll(articleMapper.getIdBySourceID(sb.getSourceID()));
        }
        //随机选择5个id，然后根据这些id去查找对应article
        for (Integer index: RamdonNumber.generateRandomNumbersInRange(0, targetArticleIDs.size(), 5)
             ) {
            targetArticles.add(articleMapper.getArticleByID(targetArticleIDs.get(index)));
        }
        for (Article article:targetArticles
             ) {
            System.out.println(article.getId());
        }
        return targetArticles;
    }
}
