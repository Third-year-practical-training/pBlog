package com.pblogteam.pblog.controller;


import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.Comment;
import com.pblogteam.pblog.service.ArticleService;
import com.pblogteam.pblog.service.CommentService;
import com.pblogteam.pblog.vo.MyComment;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;


    @RequestMapping(value = "/comment/new", method = {RequestMethod.POST})
    public ResultVO addComment(Comment comment, HttpSession session) {
        Integer id = (Integer) session.getAttribute("userId");
        if (!commentService.hasPrivilege(id)) {
            return ResultVO.throwError(400, "已被禁言");
        }
        commentService.insertComment(comment);
        return ResultVO.throwSuccess(ResponseState.SUCCESS);
    }

    @DeleteMapping("/comment/delete")
    public ResultVO deleteComment(Integer id, HttpServletRequest request) {
        if (id != null) {
            commentService.deleteCommentById(id);
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }

    @GetMapping("/comment/selectById")
    public ResultVO<PageInfo<MyComment>> getCommentListByUserId(Integer id, int pageNum) {
        if (id == null) ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        PageInfo<MyComment> myComments = commentService.selectByUserId(id, pageNum);
        if (myComments != null) {
            for (MyComment a :
                    myComments.getList()) {
                a.setContent(a.getContent().length() > Config.COMMENT_LENGTH ? a.getContent().substring(0, Config.COMMENT_LENGTH) : a.getContent());
            }
        }
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, myComments);
    }
}
