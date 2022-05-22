package com.faker.day01.DAO;

import com.faker.day01.pojo.Source;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SourceMapper {
    public List<Source> getSources();
    public Source getSourceByID(Integer id);
    public void addSource(Source source);
    public void removeSourceByID(Integer id);
    public List<String> getUrls();
    public Source getSourceByURL(@Param("url") String url);
}
