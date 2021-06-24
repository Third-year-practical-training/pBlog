package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.entity.ArticleTag;
import com.pblogteam.pblog.entity.ArticleTagExample;
import com.pblogteam.pblog.mapper.ArticleTagMapper;
import com.pblogteam.pblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleTag> selectByArticleId(Integer id) {
        return null;
    }

    @Override
    public void deleteByArticleId(Integer id) {

    }

    @Override
    public void addTagByArticleId(Integer id) {

    }

    @Override
    public void deleteByTagId(Integer id) {

    }

    @Override
    public void updateByTagId(Integer id) {

    }

    @Override
    public int isExistByName(String name) {
        ArticleTagExample articleTagExample = new ArticleTagExample();
        ArticleTagExample.Criteria articleTagEx = articleTagExample.createCriteria();
        articleTagEx.andNameEqualTo(name);
        List<ArticleTag> articleTagList = articleTagMapper.selectByExample(articleTagExample);
        if(articleTagList.size() == 0) {
            return -1;
        } else {
            return articleTagList.get(0).getId();
        }
    }

    @Override
    public List<ArticleTag> selectByTagName(String name) {
        ArticleTagExample articleTagExample = new ArticleTagExample();
        ArticleTagExample.Criteria articleTagEx = articleTagExample.createCriteria();
        articleTagEx.andNameEqualTo(name);
        return articleTagMapper.selectByExample(articleTagExample);
    }

    @Override
    public void insertAndReturnPrimaryKey(ArticleTag articleTag) {
        articleTagMapper.insertAndReturnPrimaryKey(articleTag);
    }

    @Override
    public ArticleTag selectByPrimaryKey(Integer id) {
        return articleTagMapper.selectByPrimaryKey(id);
    }
}
