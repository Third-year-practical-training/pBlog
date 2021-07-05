package com.pblogteam.pblog.service.impl;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.ArticleTag;
import com.pblogteam.pblog.entity.ArticleTagExample;
import com.pblogteam.pblog.entity.ArticleTagRela;
import com.pblogteam.pblog.mapper.ArticleTagMapper;
import com.pblogteam.pblog.mapper.ArticleTagRelaMapper;
import com.pblogteam.pblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleTagRelaMapper articleTagRelaMapper;

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

    @Override
    public List<ArticleTag> getHotTag() {
        List<ArticleTagRela> articleTagRelaList = articleTagRelaMapper.getHotTagList(Config.HOT_TAG_SIZE);
//        articleTagRelaList.sort(new Comparator<ArticleTagRela>() {
//            @Override
//            public int compare(ArticleTagRela o1, ArticleTagRela o2) {
//                 if(o1.getArticleId() > o2.getArticleId()) return 1;
//                 else if(o1.getArticleId().equals(o2.getArticleId())) return 0;
//                 else return -1;
//            }
//        });
        List<Integer> tagIds = new ArrayList<>();
        for(int i = 0;i < articleTagRelaList.size(); i++) {
            tagIds.add(articleTagRelaList.get(i).getTagId());
        }
        ArticleTagExample articleTagExample = new ArticleTagExample();
        ArticleTagExample.Criteria criteria = articleTagExample.createCriteria();
        criteria.andIdIn(tagIds);
        return articleTagMapper.selectByExample(articleTagExample);
    }

}
