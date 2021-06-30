package com.pblogteam.pblog.service;


import com.pblogteam.pblog.entity.ArticleTag;

import java.util.List;

public interface TagService {
    List<ArticleTag> selectByArticleId(Integer id);
    void deleteByArticleId(Integer id);
    void addTagByArticleId(Integer id);
    void deleteByTagId(Integer id);
    void updateByTagId(Integer id);
    int isExistByName(String name);
    List<ArticleTag> selectByTagName(String name);
    void insertAndReturnPrimaryKey(ArticleTag articleTag);
    ArticleTag selectByPrimaryKey(Integer id);
    List<ArticleTag> getHotTag();
}
