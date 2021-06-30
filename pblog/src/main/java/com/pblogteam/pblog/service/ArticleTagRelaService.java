package com.pblogteam.pblog.service;


import com.pblogteam.pblog.entity.ArticleTagRela;

import java.util.List;

public interface ArticleTagRelaService {
    boolean isExist(ArticleTagRela articleTagRela);
    void insert(ArticleTagRela articleTagRela);
    List<ArticleTagRela> selectByArticleId(Integer id);
    void deleteByArticleId(Integer id);
    List<Integer> selectArticleIdsByTagId(int tagId);
    List<ArticleTagRela> getHotTagList();
}
