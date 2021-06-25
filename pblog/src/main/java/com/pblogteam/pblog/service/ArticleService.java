package com.pblogteam.pblog.service;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.entity.Article;
import com.pblogteam.pblog.entity.ArticleTag;
import com.pblogteam.pblog.vo.ArticleAndCommentVO;
import com.pblogteam.pblog.vo.ArticleNewVO;
import com.pblogteam.pblog.vo.ArticleTitleVO;
import java.util.List;

public interface ArticleService {
    PageInfo<ArticleTitleVO> selectArtOrDraListByUserId(Integer id, int flag, int pageNum);

    public List<ArticleTitleVO> selectByTypeId(Integer id);
    public boolean deleteArticleById(Integer id);
    public void addAndUpdate(ArticleNewVO articleNewVO, Byte published);
    public ArticleAndCommentVO selectByArticleId(Integer id, Integer curUserId);
    public List<ArticleTitleVO> selectCollectListByUserId(Integer id);
    public void changeCollection(Integer userId, Integer articleId);
    public boolean isArticle(Integer id);
    public void publishDraft(Integer id);
//    List<ArticleTitleVO> selectByKeyWord
}
