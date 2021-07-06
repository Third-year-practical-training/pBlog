package com.pblogteam.pblog.service;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.entity.Comment;
import com.pblogteam.pblog.vo.CommentVO;
import com.pblogteam.pblog.vo.MyComment;

import java.util.List;

public interface CommentService {
    void deleteCommentById(Integer id);
    void insertComment(Comment comment);
//    void updateComment(Comment comment);
    List<CommentVO> selectByArticleId(Integer id);
    void deleteByArticleId(Integer id);
    PageInfo<MyComment> selectByUserId(Integer id, int pageNum);
    boolean hasPrivilege(Integer id);
}
