package com.pblogteam.pblog.service;

import com.pblogteam.pblog.entity.Comment;

import java.util.List;

public interface CommentService {
    void deleteCommentById(Integer id);
    void insertComment(Comment comment);
    void updateComment(Comment comment);
    public List<Comment> selectByArticleId(Integer id);
    public boolean deleteByArticleId(Integer id);
    List<Comment> selectByUserId(Integer id);
}
