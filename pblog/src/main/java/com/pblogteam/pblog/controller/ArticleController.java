package com.pblogteam.pblog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.service.impl.ArticleServiceImpl;
import com.pblogteam.pblog.vo.ArticleAndCommentVO;
import com.pblogteam.pblog.vo.ArticleNewVO;
import com.pblogteam.pblog.vo.ArticleTitleVO;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleServiceImpl;
    @GetMapping("/articles/findByUserId")
    public ResultVO<PageInfo<ArticleTitleVO>> getArticleListByUser(Integer id, int pageNum) {
        if(id != null) {
            PageInfo<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectArtOrDraListByUserId(id, 1, pageNum);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }

    @GetMapping("/drafts/findByUserId")
    public ResultVO<PageInfo<ArticleTitleVO>> getDraftListByUser(Integer id, int pageNum) {
        ResultVO<List<ArticleTitleVO>> resultVO = new ResultVO<>();
        if(id != null) {
            PageInfo<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectArtOrDraListByUserId(id, 0, pageNum  );
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }

    @GetMapping("/articles/findByType")
    public ResultVO<PageInfo<ArticleTitleVO>> getArticleListByType(Integer id, int pageNum) {
        if(id != null) {
            PageInfo<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectByTypeId(id, pageNum);
            return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleTitleVOList);
        }
        return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
    }

    @GetMapping("/articles/collectList")
    public ResultVO<PageInfo<ArticleTitleVO>> getCollArtByUserId(Integer id, int pageNum) {
        ResultVO<PageInfo<ArticleTitleVO>> resultVO = new ResultVO<>();
        if(id != null) {
            PageInfo<ArticleTitleVO> articleTitleVOList = articleServiceImpl.selectCollectListByUserId(id, pageNum);
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



    /**
     *
     * @param keyWord   要查询的关键字
     * @param pageNum   分页
     * @param type  查询的范围 0 全站，1类型，2标签
     * @param id    类型id或tag id
     * @return 查询结果
     */
    @GetMapping("/article/searchByKeyWord")
    public ResultVO<PageInfo<ArticleTitleVO>> searchByKeyWord(String keyWord, int pageNum, int type, int id) {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.selectArticleByKeyWord(keyWord, type, id, pageNum));
    }

    @GetMapping("/article/findAllArticle")
    public ResultVO<PageInfo<ArticleTitleVO>> findAllArticle(int pageNum) {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.showAllArticle(pageNum));
    }
}
