package com.pblogteam.pblog.service;


import com.pblogteam.pblog.entity.ArticleTagRela;

import java.util.List;

public interface ArticleTagRelaService {
    public boolean isExist(ArticleTagRela articleTagRela);
    public void insert(ArticleTagRela articleTagRela);
    public List<ArticleTagRela> selectByArticleId(Integer id);
    public void deleteByArticleId(Integer id);
}
