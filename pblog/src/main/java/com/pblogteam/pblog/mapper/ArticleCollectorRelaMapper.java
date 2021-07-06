package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.ArticleCollectorRela;
import com.pblogteam.pblog.entity.ArticleCollectorRelaExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ArticleCollectorRelaMapper {
    //    my own start
    List<ArticleCollectorRela> getHotArticleList(int num);

    //    my own end
    long countByExample(ArticleCollectorRelaExample example);

    int deleteByExample(ArticleCollectorRelaExample example);

    int insert(ArticleCollectorRela record);

    int insertSelective(ArticleCollectorRela record);

    List<ArticleCollectorRela> selectByExample(ArticleCollectorRelaExample example);

    int updateByExampleSelective(@Param("record") ArticleCollectorRela record, @Param("example") ArticleCollectorRelaExample example);

    int updateByExample(@Param("record") ArticleCollectorRela record, @Param("example") ArticleCollectorRelaExample example);
}