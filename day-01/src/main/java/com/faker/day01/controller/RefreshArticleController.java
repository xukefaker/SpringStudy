package com.faker.day01.controller;

import com.faker.day01.DAO.ArticleMapper;
import com.faker.day01.DAO.SourceMapper;
import com.faker.day01.DAO.SubscribeRecordMapper;
import com.faker.day01.DAO.UserMapper;
import com.faker.day01.pojo.Article;
import com.faker.day01.pojo.Source;
import com.faker.day01.pojo.SubscribeRecord;
import com.faker.day01.utils.IndexesOfSubstring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class RefreshArticleController {
    final String PRE="http://124.220.159.176:1200/";
    //下面几个length是帮助取XML里面的各个数据的，都是信息前面标签的长度
    final Integer TITLE_PREFIX_LENGTH = 16;
    final Integer DESCRIPTION_PREFIX_LENGTH = 22;
    final Integer LINK_PREFIX_LENGTH = 6;
    final Integer PUBDATE_PREFIX_LENGTH = 9;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SubscribeRecordMapper subscribeRecordMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SourceMapper sourceMapper;

    @RequestMapping("/user/refreshArticles")
    @ResponseBody //如果这个函数仅仅是要处理某个数据而不要求页面跳转，那么就要加这个注解
    public void refreshArticles(String username) throws Exception {
        Integer userID = userMapper.getUserByUserName(username).getId();

        List<Integer> titleIndexes = null;
        List<Integer> linkIndexes = null;
        List<Integer> descriptionIndexes = null;
        List<Integer> pubDateIndexes = null;
        List<Integer> slashTitleIndexes = null;
        List<Integer> slashLinkIndexes = null;
        List<Integer> slashDescriptionIndexes = null;
        List<Integer> slashPubDateIndexes = null;
        List<Integer> urlSlashIndexes = null;
        List<Integer> sourceIDs = new ArrayList<>();
        Article article = null;
        Integer sourceID;
        String platform = "";

        String res = "";
        String location = "";
        HttpURLConnection connection = null;
        InputStream inputStream=null;

        List<SubscribeRecord> subscribeRecords = subscribeRecordMapper.getSubscribeRecordByUserID(userID);
        List<String> urls = new ArrayList<>();
        String url;

        for (SubscribeRecord subscribeRecord: subscribeRecords
        ) {
            sourceID = subscribeRecord.getSourceID();
            sourceIDs.add(sourceID);
            urls.add(sourceMapper.getSourceByID(sourceID).getUrl());
        }
        for(int i = 0; i < urls.size(); i++){
            url = urls.get(i);
            res="";
            location=PRE+url;
            connection=(HttpURLConnection) new URL(location).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(1000);
//            System.out.println(connection.getResponseCode());
            if(!url.trim().isEmpty() && connection.getResponseCode()==200){
                //更新这个URL的文章前，先把数据库原来的数据删掉，否则数据量会很大
                articleMapper.removeArticleBySourceID(sourceIDs.get(i));
                System.out.println(url);
                inputStream = connection.getInputStream();
                if(inputStream==null) continue;
                res=new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(""));
                titleIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "<title><![CDATA[");
                linkIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "<link>");
                descriptionIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "<description><![CDATA[");
                pubDateIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "<pubDate>");
                slashTitleIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "]]></title>");
                slashLinkIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "</link>");
                slashDescriptionIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "]]></description>");
                slashPubDateIndexes = IndexesOfSubstring.getIndexesOfSubstring(res, "</pubDate>");
//                System.out.println(res.substring(titleIndexes.get(1)+TITLE_PREFIX_LENGTH, slashTitleIndexes.get(1)));
//                System.out.println(res.substring(linkIndexes.get(1) + LINK_PREFIX_LENGTH, slashLinkIndexes.get(1)));
//                System.out.println(res.substring(descriptionIndexes.get(1) + DESCRIPTION_PREFIX_LENGTH, slashDescriptionIndexes.get(1)));
//                System.out.println(res.substring(pubDateIndexes.get(1) + PUBDATE_PREFIX_LENGTH, slashPubDateIndexes.get(1)));
                //由于首个title对应的元素没有任何信息，所以需要删除,但是pubDate不需要，因为首个title对应的pubDate是lastBuildDate
                titleIndexes.remove(0);
                descriptionIndexes.remove(0);
                linkIndexes.remove(0);
                slashTitleIndexes.remove(0);
                slashDescriptionIndexes.remove(0);
                slashLinkIndexes.remove(0);
                //获取url中的platform
                urlSlashIndexes = IndexesOfSubstring.getIndexesOfSubstring(url, "/");
                platform = url.substring(0, url.indexOf("/"));
                //遍历每个item，将信息插入数据库
                for(int j = 0; j < titleIndexes.size(); j++){
                    article = new Article();
                    //sourceID由i控制
                    article.setSourceID(sourceIDs.get(i));
                    //下面的index由j控制
                    article.setPubDate(res.substring(pubDateIndexes.get(j) + PUBDATE_PREFIX_LENGTH, slashPubDateIndexes.get(j)));
                    article.setTitle(res.substring(titleIndexes.get(j)+TITLE_PREFIX_LENGTH, slashTitleIndexes.get(j)));
                    article.setDescription(res.substring(descriptionIndexes.get(j) + DESCRIPTION_PREFIX_LENGTH, slashDescriptionIndexes.get(j)));
                    article.setLink(res.substring(linkIndexes.get(j) + LINK_PREFIX_LENGTH, slashLinkIndexes.get(j)));
                    article.setPlatform(platform);
                    articleMapper.addArticle(article);
                }
//                System.out.println(titleIndexes.size() + " " + descriptionIndexes.size() + " " + linkIndexes.size() + " " + pubDateIndexes.size() +" " + slashDescriptionIndexes.size() + " " + slashLinkIndexes.size() + " " + slashTitleIndexes.size() + " " + slashPubDateIndexes.size());
            }
            else {
                //如果这个链接非法，就把它干掉
                sourceMapper.removeSourceByID(sourceIDs.get(i));
            }
        }
    }
    //title和description可能出现uft8mb4编码的字符，不知道为什么阿里云的RDS死活插不进去，只能把他们过滤掉
    private static String filter(String str) {
        if(str.trim().isEmpty()){
            return str;
        }
        String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr="";
        Pattern emoji=Pattern.compile(pattern);
        Matcher emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;
    }
}
