package com.pblogteam.pblog.controller;


import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.Comment;
import com.pblogteam.pblog.service.ArticleService;
import com.pblogteam.pblog.service.CommentService;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;


    @RequestMapping(value = "/comment/new", method = {RequestMethod.POST})
    public ResultVO<String> addComment(@RequestParam("userId") Integer userId, @RequestParam("articleId") Integer articleId,
                                       @RequestParam("date") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date,
                                       @RequestParam("content") String content, Comment comment, HttpSession session) {
        if (userId != null & articleId != null & date != null & content != null) {
            commentService.insertComment(comment);
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/comment/delete", method = {RequestMethod.DELETE})
    public ResultVO<String> deleteComment(@RequestParam("id") Integer id, Comment comment, HttpSession session) {
        if (id != null) {
            commentService.deleteCommentById(comment.getId());
            return ResultVO.throwSuccess(ResponseState.SUCCESS);
        } else {
            return ResultVO.throwError(ResponseState.NOT_FOUND);
        }
    }

    @GetMapping("/comment/selectById")
    public ResultVO<List<Comment>> getCommentListByUserId(Integer id) {
        if(id == null) ResultVO.throwError(ResponseState.BODY_NOT_MATCH);
        List<Comment> commentList = commentService.selectByUserId(id);
        if(commentList != null) {
            for (Comment a :
                    commentList) {
                a.setContent(a.getContent().length() > 100 ? a.getContent().substring(0, 100) : a.getContent());
            }
        }
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, commentList);
    }
}
