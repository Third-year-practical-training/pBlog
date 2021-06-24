package com.pblogteam.pblog.controller;

import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.service.impl.ArticleServiceImpl;
import com.pblogteam.pblog.vo.ArticleAndCommentVO;
import com.pblogteam.pblog.vo.ArticleNewVO;
import com.pblogteam.pblog.vo.ArticleTitleVO;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleServiceImpl;
    @GetMapping("/articles/findByUserId")
    public ResultVO<List<ArticleTitleVO>> getArticleListByUser(Integer id) {
        if(id != null) {
            List<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectArtOrDraListByUserId(id, 1);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }

    @GetMapping("/drafts/findByUserId")
    public ResultVO<List<ArticleTitleVO>> getDraftListByUser(Integer id) {
        ResultVO<List<ArticleTitleVO>> resultVO = new ResultVO<>();
        if(id != null) {
            List<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectArtOrDraListByUserId(id, 0);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }

    @GetMapping("/articles/findByType")
    public ResultVO<List<ArticleTitleVO>> getArticleListByType(Integer id) {
        if(id != null) {
            List<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectByTypeId(id);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }

    @GetMapping("/articles/collectList")
    public ResultVO<List<ArticleTitleVO>> getCollArtByUserId(Integer id) {
        ResultVO<List<ArticleTitleVO>> resultVO = new ResultVO<>();
        if(id != null) {
            List<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectCollectListByUserId(id);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }



    @GetMapping("/article/findById")
    public ResultVO<ArticleAndCommentVO> getArticleContentById(Integer id, HttpServletRequest request) {
        if(!articleServiceImpl.isArticle(id) || id == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        int userId = (int) request.getSession().getAttribute("userId");
        if(id == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.selectByArticleId(id, userId));
    }

    @GetMapping("/draft/findById")
    public ResultVO<ArticleAndCommentVO> getDraftContentById(Integer id, HttpServletRequest request) {
        ResultVO<ArticleAndCommentVO> resultVO = new ResultVO<>();
        if(articleServiceImpl.isArticle(id) || id == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        int userId = (int) request.getSession().getAttribute("userId");
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.selectByArticleId(id, userId));
    }

    @PutMapping("/article/changeCollection")
    public ResultVO changeCollectStatus(Integer userId, Integer articleId) {
        if(!articleServiceImpl.isArticle(articleId) || userId == null || articleId == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        articleServiceImpl.changeCollection(userId, articleId);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PostMapping(value = "/article/new")
    public ResultVO addArticle(@RequestBody ArticleNewVO articleNewVO) {
        articleServiceImpl.addAndUpdate(articleNewVO, (byte) 1);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PostMapping("/draft/new")
    public ResultVO addDraft(@RequestBody ArticleNewVO articleNewVO) {
        articleServiceImpl.addAndUpdate(articleNewVO, (byte) 0);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PutMapping("/article/update")
    public ResultVO updateArticle(@RequestBody ArticleNewVO articleNewVO) {
        if(articleNewVO.getId() != null && !articleServiceImpl.isArticle(articleNewVO.getId())) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        articleServiceImpl.addAndUpdate(articleNewVO, (byte) 1);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PutMapping("/draft/update")
    public ResultVO updateDraft(@RequestBody ArticleNewVO articleNewVO) {
        if(articleNewVO.getId() != null && articleServiceImpl.isArticle(articleNewVO.getId())) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        articleServiceImpl.addAndUpdate(articleNewVO, (byte) 0);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @DeleteMapping("/article/deleteById")
    public ResultVO deleteArticle(Integer id) {
        if(!articleServiceImpl.isArticle(id) || id == null) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        if(articleServiceImpl.deleteArticleById(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }

    @DeleteMapping("/draft/deleteById")
    public ResultVO deleteDraft(Integer id) {
        if(articleServiceImpl.isArticle(id) || id == null) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        if(articleServiceImpl.deleteArticleById(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }

    @PutMapping("/draft/publishById")
    public ResultVO publishDraft(Integer id) {
        if(articleServiceImpl.isArticle(id) || id == null) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        articleServiceImpl.publishDraft(id);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

}
