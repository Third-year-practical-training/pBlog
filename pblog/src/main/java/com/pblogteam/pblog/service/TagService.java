package com.pblogteam.pblog.service;


import com.pblogteam.pblog.entity.ArticleTag;

import java.util.List;

public interface TagService {
    public List<ArticleTag> selectByArticleId(Integer id);
    public void deleteByArticleId(Integer id);
    public void addTagByArticleId(Integer id);
    public void deleteByTagId(Integer id);
    public void updateByTagId(Integer id);
    public int isExistByName(String name);
    public List<ArticleTag> selectByTagName(String name);
    public void insertAndReturnPrimaryKey(ArticleTag articleTag);
    public ArticleTag selectByPrimaryKey(Integer id);
}
