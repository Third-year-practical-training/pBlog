package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.entity.ArticleTagRela;
import com.pblogteam.pblog.entity.ArticleTagRelaExample;
import com.pblogteam.pblog.mapper.ArticleTagMapper;
import com.pblogteam.pblog.mapper.ArticleTagRelaMapper;
import com.pblogteam.pblog.service.ArticleTagRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTagRelaServiceImpl implements ArticleTagRelaService {
    @Autowired
    private ArticleTagRelaMapper articleTagRelaMapper;

    @Override
    public boolean isExist(ArticleTagRela articleTagRela) {
        ArticleTagRelaExample articleTagRelaExample = new ArticleTagRelaExample();
        ArticleTagRelaExample.Criteria articleTagRelaEx = articleTagRelaExample.createCriteria();
        articleTagRelaEx.andArticleIdEqualTo(articleTagRela.getArticleId());
        articleTagRelaEx.andTagIdEqualTo(articleTagRela.getTagId());
        return articleTagRelaMapper.selectByExample(articleTagRelaExample).size() > 0;
    }

    @Override
    public void insert(ArticleTagRela articleTagRela) {
        if(!isExist(articleTagRela)) {
            articleTagRelaMapper.insert(articleTagRela);
        }
    }

    @Override
    public List<ArticleTagRela> selectByArticleId(Integer id) {
        ArticleTagRelaExample articleTagRelaExample = new ArticleTagRelaExample();
        ArticleTagRelaExample.Criteria articleTagRelaEx = articleTagRelaExample.createCriteria();
        articleTagRelaEx.andArticleIdEqualTo(id);
        return articleTagRelaMapper.selectByExample(articleTagRelaExample);
    }

    @Override
    public void deleteByArticleId(Integer id) {
        ArticleTagRelaExample articleTagRelaExample = new ArticleTagRelaExample();
        ArticleTagRelaExample.Criteria articleTagRelaEx = articleTagRelaExample.createCriteria();
        articleTagRelaEx.andArticleIdEqualTo(id);
        articleTagRelaMapper.deleteByExample(articleTagRelaExample);
    }

    @Override
    public List<Integer> selectArticleIdsByTagId(int tagId) {
        ArticleTagRelaExample articleTagRelaExample = new ArticleTagRelaExample();
        ArticleTagRelaExample.Criteria criteria = articleTagRelaExample.createCriteria();
        criteria.andTagIdEqualTo(tagId);
        List<ArticleTagRela> articleTagRelaList = articleTagRelaMapper.selectByExample(articleTagRelaExample);
        List<Integer> list = new ArrayList<>();
        for (ArticleTagRela item:
             articleTagRelaList) {
            list.add(item.getArticleId());
        }
        return list;
    }

    @Override
    public List<ArticleTagRela> getHotTagList() {
        return null;
    }


}
