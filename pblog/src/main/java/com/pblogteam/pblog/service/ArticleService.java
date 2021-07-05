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

    PageInfo<ArticleTitleVO> showAllArticle(int pageNum);

    PageInfo<ArticleTitleVO> selectByTypeId(Integer id, int pageNum);


    boolean deleteArticleById(Integer id);

    void addAndUpdate(ArticleNewVO articleNewVO, Byte published);

    ArticleAndCommentVO selectByArticleId(Integer id, Integer curUserId);

    PageInfo<ArticleTitleVO> selectCollectListByUserId(Integer id, int pageNum);

    void changeCollection(Integer userId, Integer articleId);

    boolean isArticle(Integer id);

    void publishDraft(Integer id);



    /**
     *
     * @param keyWord 查询需要的关键字
     * @param type 在什么范围内查询
     * @return 查询结果
     */
    PageInfo<ArticleTitleVO> selectArticleByKeyWord(String keyWord, int type, int id, int pageNum);
}
