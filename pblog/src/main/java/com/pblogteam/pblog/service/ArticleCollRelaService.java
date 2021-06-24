package com.pblogteam.pblog.service;

import com.pblogteam.pblog.entity.ArticleCollectorRela;

import java.util.List;

public interface ArticleCollRelaService {
    public void changeCollStatus(ArticleCollectorRela articleCollectorRela);
    public boolean isExist(ArticleCollectorRela articleCollectorRela);
    public void insert(ArticleCollectorRela articleCollectorRela);
    public void delete(ArticleCollectorRela articleCollectorRela);
    public void deleteByArticleId(Integer id);
    public List<ArticleCollectorRela> selectByUserId(Integer id);
}
