package com.pblogteam.pblog.service;

import com.pblogteam.pblog.entity.ArticleCollectorRela;
import com.pblogteam.pblog.vo.ArticleTitleVO;

import java.util.List;

public interface ArticleCollRelaService {
    void changeCollStatus(ArticleCollectorRela articleCollectorRela);
    boolean isExist(ArticleCollectorRela articleCollectorRela);
    void insert(ArticleCollectorRela articleCollectorRela);
    void delete(ArticleCollectorRela articleCollectorRela);
    void deleteByArticleId(Integer id);
    List<ArticleCollectorRela> selectByUserId(Integer id);
    List<ArticleTitleVO> getHotList();
}
