package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.entity.ArticleType;
import com.pblogteam.pblog.entity.ArticleTypeExample;
import com.pblogteam.pblog.mapper.ArticleTypeMapper;
import com.pblogteam.pblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private ArticleTypeMapper articleTypeMapper;
    @Override
    public List<ArticleType> ListType() {
        ArticleTypeExample articleTypeExample = new ArticleTypeExample();
        ArticleTypeExample.Criteria articleTypeEx = articleTypeExample.createCriteria();
        articleTypeEx.getAllCriteria();
        return articleTypeMapper.selectByExample(articleTypeExample);
    }

    @Override
    public boolean saveType(String type) {
        return articleTypeMapper.insertSelective(new ArticleType(null, type)) == 1;
    }

    @Override
    public boolean updateTypeById(String type, Integer id) {
        return articleTypeMapper.updateByPrimaryKey(new ArticleType(id, type)) == 1;
    }

    @Override
    public boolean deleteTypeById(Integer id) {
        return articleTypeMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public ArticleType findTypeByName(String typeName) {
        return null;
    }
}
