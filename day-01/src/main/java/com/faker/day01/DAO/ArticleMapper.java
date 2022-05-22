package com.faker.day01.DAO;

import com.faker.day01.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    public void addArticle(Article article);
    public Article getArticleByID(Integer id);
    public List<Article> getArticles();
    public List<Article> getArticlesBySourceID(Integer id);
    public List<Integer> getIdBySourceID(Integer id);
    public void removeArticleBySourceID(Integer id);
}
