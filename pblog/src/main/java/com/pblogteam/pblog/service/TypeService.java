package com.pblogteam.pblog.service;

import com.pblogteam.pblog.entity.ArticleType;

import java.util.List;

public interface TypeService {

    List<ArticleType> ListType();

    boolean saveType(String type);

    boolean updateTypeById(String type, Integer id);

    boolean deleteTypeById(Integer id);

    ArticleType findTypeByName(String typeName);

    ArticleType findTypeById(Integer id);
}
