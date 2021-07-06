package com.pblogteam.pblog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.collection.dartsclone.DartMap;
import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.service.ArticleCollRelaService;
import com.pblogteam.pblog.service.impl.ArticleServiceImpl;
import com.pblogteam.pblog.vo.ArticleAndCommentVO;
import com.pblogteam.pblog.vo.ArticleNewVO;
import com.pblogteam.pblog.vo.ArticleTitleVO;
import com.pblogteam.pblog.vo.ResultVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleServiceImpl;
    @Autowired
    private ArticleCollRelaService articleCollRelaService;
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
        Integer curId = (Integer) request.getSession().getAttribute("userId");
        if(!articleServiceImpl.isArticle(id, curId) || id == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        int userId = (int) request.getSession().getAttribute("userId");
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.selectByArticleId(id, userId));
    }

    @GetMapping("/draft/findById")
    public ResultVO<ArticleAndCommentVO> getDraftContentById(Integer id, HttpServletRequest request) {
        ResultVO<ArticleAndCommentVO> resultVO = new ResultVO<>();
        int userId = (int) request.getSession().getAttribute("userId");
        if(articleServiceImpl.isArticle(id, userId) || id == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.selectByArticleId(id, userId));
    }

    @PutMapping("/article/changeCollection")
    public ResultVO changeCollectStatus(Integer userId, Integer articleId) {
        if(!articleServiceImpl.isArticle(articleId, -1) || userId == null || articleId == null) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
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
    public ResultVO updateArticle(@RequestBody ArticleNewVO articleNewVO, HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute("userId");
        if(articleNewVO.getId() != null && !articleServiceImpl.isArticle(articleNewVO.getId(), userId)) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        articleServiceImpl.addAndUpdate(articleNewVO, (byte) 1);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @PutMapping("/draft/update")
    public ResultVO updateDraft(@RequestBody ArticleNewVO articleNewVO, HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute("userId");
        if(articleNewVO.getId() != null && articleServiceImpl.isArticle(articleNewVO.getId(), userId)) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        articleServiceImpl.addAndUpdate(articleNewVO, (byte) 0);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @DeleteMapping("/article/deleteById")
    public ResultVO deleteArticle(Integer id, HttpServletRequest request) {
        Integer curId = (Integer) request.getSession().getAttribute("userId");
        if(!articleServiceImpl.isArticle(id, curId) || id == null) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        if(articleServiceImpl.deleteArticleById(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }
    @DeleteMapping("/admin/deleteArticle")
    public ResultVO deleteArticleAdmin(Integer id) {
        if(articleServiceImpl.isArticle(id, -1)) {
            articleServiceImpl.deleteArticleById(id);
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        }
        return ResultVO.throwError(ResponseState.NOT_FOUND);
    }

    @PutMapping("/admin/changeFeature")
    public ResultVO changeFeature(Integer id) {
        articleServiceImpl.changeFeature(id);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @DeleteMapping("/draft/deleteById")
    public ResultVO deleteDraft(Integer id, HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute("userId");
        if(articleServiceImpl.isArticle(id, userId) || id == null) {
            return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        }
        if(articleServiceImpl.deleteArticleById(id)) {
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }

    @PutMapping("/draft/publishById")
    public ResultVO publishDraft(Integer id, HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute("userId");
        if(articleServiceImpl.isArticle(id, userId) || id == null) {
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
     * @param id    类型id 或 tag id
     * @return 查询结果
     */
    @GetMapping("/article/searchByKeyWord")
    public ResultVO<PageInfo<ArticleTitleVO>> searchByKeyWord(String keyWord, int pageNum, int type, int id) {
        if(keyWord == null || keyWord.equals("") || keyWord.contains("%")) return ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleServiceImpl.selectArticleByKeyWord(keyWord, type, id, pageNum));
    }

    @GetMapping("/article/findAllArticle")
    public ResultVO<PageInfo<ArticleTitleVO>> findAllArticle(int pageNum) {
        System.out.println(System.currentTimeMillis());
        PageInfo<ArticleTitleVO> tmp = articleServiceImpl.showAllArticle(pageNum);
        System.out.println(System.currentTimeMillis());
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, tmp);
    }

    @GetMapping("/article/getHotArticles")
    public ResultVO<List<ArticleTitleVO>> getHotArticles() {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, articleCollRelaService.getHotList());
    }
}
