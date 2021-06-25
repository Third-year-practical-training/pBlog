package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.entity.ArticleType;
import com.pblogteam.pblog.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {


    @Override
    public List<ArticleType> ListType() {
        return null;
    }

    @Override
    public void saveType(ArticleType type) {

    }

    @Override
    public void updateTypeById(ArticleType type, Integer id) {

    }

    @Override
    public void deleteTypeById(Integer id) {

    }

    @Override
    public ArticleType findTypeByName(String typeName) {
        return null;
    }
}
