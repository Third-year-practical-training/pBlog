package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.entity.ArticleCollectorRela;
import com.pblogteam.pblog.entity.ArticleCollectorRelaExample;
import com.pblogteam.pblog.mapper.ArticleCollectorRelaMapper;
import com.pblogteam.pblog.service.ArticleCollRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCollRelaServiceImpl implements ArticleCollRelaService {
    @Autowired
    private ArticleCollectorRelaMapper articleCollectorRelaMapper;
    @Override
    public void changeCollStatus(ArticleCollectorRela articleCollectorRela) {
        if(isExist(articleCollectorRela)) {
            delete(articleCollectorRela);
        } else {
            insert(articleCollectorRela);
        }
    }



    @Override
    public boolean isExist(ArticleCollectorRela articleCollectorRela) {
        ArticleCollectorRelaExample example = new ArticleCollectorRelaExample();
        ArticleCollectorRelaExample.Criteria ex = example.createCriteria();
        ex.andArticleIdEqualTo(articleCollectorRela.getArticleId());
        ex.andUserIdEqualTo(articleCollectorRela.getUserId());
        return articleCollectorRelaMapper.selectByExample(example).size() > 0;
    }

    @Override
    public void insert(ArticleCollectorRela articleCollectorRela) {
        articleCollectorRelaMapper.insert(articleCollectorRela);
    }

    @Override
    public void delete(ArticleCollectorRela articleCollectorRela) {
        ArticleCollectorRelaExample example = new ArticleCollectorRelaExample();
        ArticleCollectorRelaExample.Criteria ex = example.createCriteria();
        ex.andArticleIdEqualTo(articleCollectorRela.getArticleId());
        ex.andUserIdEqualTo(articleCollectorRela.getUserId());
        articleCollectorRelaMapper.deleteByExample(example);
    }

    @Override
    public void deleteByArticleId(Integer id) {
        ArticleCollectorRelaExample example = new ArticleCollectorRelaExample();
        ArticleCollectorRelaExample.Criteria ex = example.createCriteria();
        ex.andArticleIdEqualTo(id);
        articleCollectorRelaMapper.deleteByExample(example);
    }

    @Override
    public List<ArticleCollectorRela> selectByUserId(Integer id) {
        ArticleCollectorRelaExample example = new ArticleCollectorRelaExample();
        ArticleCollectorRelaExample.Criteria ex = example.createCriteria();
        ex.andUserIdEqualTo(id);
        return articleCollectorRelaMapper.selectByExample(example);
    }


}
