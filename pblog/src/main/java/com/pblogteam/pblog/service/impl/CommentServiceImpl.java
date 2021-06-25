package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.Comment;
import com.pblogteam.pblog.entity.CommentExample;
import com.pblogteam.pblog.mapper.CommentMapper;
import com.pblogteam.pblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void deleteCommentById(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    public List<Comment> selectByArticleId(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria commentEx = commentExample.createCriteria();
        commentEx.andArticleIdEqualTo(id);
        return commentMapper.selectByExampleWithBLOBs(commentExample);
    }

    @Override
    public boolean deleteByArticleId(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria commentEx = commentExample.createCriteria();
        commentEx.andArticleIdEqualTo(id);
        return commentMapper.deleteByExample(commentExample) != 0;
    }

    @Override
    public PageInfo<Comment> selectByUserId(Integer id, int pageNum) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria commentEx = commentExample.createCriteria();
        commentEx.andUserIdEqualTo(id);
        PageHelper.startPage(pageNum, Config.PAGE_SIZE);
        List<Comment> commentList = commentMapper.selectByExampleWithBLOBs(commentExample);
        return new PageInfo<>(commentList);
    }
}
