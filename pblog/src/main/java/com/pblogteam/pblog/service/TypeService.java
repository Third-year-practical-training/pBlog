package com.pblogteam.pblog.service;

import com.pblogteam.pblog.entity.ArticleType;

import java.util.List;

public interface TypeService {

    List<ArticleType> ListType();
    void saveType(ArticleType type);
    void updateTypeById(ArticleType type,Integer id);
    void deleteTypeById(Integer id);
    ArticleType findTypeByName(String typeName);
}
