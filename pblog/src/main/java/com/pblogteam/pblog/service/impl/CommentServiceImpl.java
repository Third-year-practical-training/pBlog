package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.Article;
import com.pblogteam.pblog.entity.Comment;
import com.pblogteam.pblog.entity.CommentExample;
import com.pblogteam.pblog.entity.User;
import com.pblogteam.pblog.mapper.ArticleMapper;
import com.pblogteam.pblog.mapper.CommentMapper;
import com.pblogteam.pblog.mapper.UserMapper;
import com.pblogteam.pblog.service.CommentService;
import com.pblogteam.pblog.util.CopyPageInfo;
import com.pblogteam.pblog.vo.CommentVO;
import com.pblogteam.pblog.vo.MyComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void deleteCommentById(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andFatherIdEqualTo(id);
        commentMapper.deleteByPrimaryKey(id);
        commentMapper.deleteByExample(commentExample);
    }

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insertSelective(comment);
        Article article = articleMapper.selectByPrimaryKey(comment.getArticleId());
        article.setCommentCount(article.getCommentCount() + 1);
    }

    @Override
    public List<CommentVO> selectByArticleId(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = null;
        commentExample.setOrderByClause("date ASC, id ASC");
        criteria = commentExample.createCriteria();
        criteria.andArticleIdEqualTo(id);
        criteria.andFatherIdIsNull();
        List<Comment> commentList = commentMapper.selectByExampleWithBLOBs(commentExample);
        List<CommentVO> commentVOList = fillCommentVOByComment(commentList);
        for(CommentVO commentVO: commentVOList) {
            commentExample.clear();
            criteria = commentExample.createCriteria();
            criteria.andFatherIdEqualTo(commentVO.getCommentId());
            commentList = commentMapper.selectByExampleWithBLOBs(commentExample);
            commentVO.setChildList(fillCommentVOByComment(commentList));
        }
        return commentVOList;
    }

    public CommentVO castCommentToCommentVO(Comment comment) {
        if(comment == null) return null;
        CommentVO commentVO = new CommentVO();
        commentVO.setCommentId(comment.getId());
        commentVO.setContent(comment.getContent());
        commentVO.setDate(comment.getDate());
        commentVO.setFromUserId(comment.getUserId());
        User fromUser = userMapper.selectByPrimaryKey(comment.getUserId());
        commentVO.setPhoto(fromUser.getPhotoUrl());
        commentVO.setFromUserNickName(fromUser.getNickname());
        commentVO.setFromUserId(fromUser.getId());
        commentVO.setFatherId(comment.getFatherId());
        commentVO.setChildList(null);
        if(comment.getToId() != null) {
            commentVO.setToUserId(comment.getToId());
            User toUser = userMapper.selectByPrimaryKey(comment.getToId());
            commentVO.setToUserNickName(toUser.getNickname());
        }
        return commentVO;
    }

    public List<CommentVO> fillCommentVOByComment(List<Comment> commentList) {
        if(commentList == null) return null;
        List<CommentVO> commentVOList = new ArrayList<>();
        for(Comment comment: commentList) {
            commentVOList.add(castCommentToCommentVO(comment));
        }
        return commentVOList;
    }


    @Override
    public void deleteByArticleId(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria commentEx = commentExample.createCriteria();
        commentEx.andArticleIdEqualTo(id);
        commentMapper.deleteByExample(commentExample);
        commentExample.clear();
        commentEx = commentExample.createCriteria();
        commentEx.andFatherIdEqualTo(id);
        commentMapper.deleteByExample(commentExample);
    }

    @Override
    public PageInfo<MyComment> selectByUserId(Integer id, int pageNum) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria commentEx = commentExample.createCriteria();
        commentEx.andUserIdEqualTo(id);
        PageHelper.startPage(pageNum, Config.PAGE_SIZE);
        List<Comment> commentList = commentMapper.selectByExampleWithBLOBs(commentExample);
        List<MyComment> myComments = new ArrayList<>();
        for(Comment comment: commentList) {
            myComments.add(new MyComment(comment, articleMapper.selectByPrimaryKey(comment.getArticleId()).getTitle()));
        }
        return CopyPageInfo.covertPageInfo(myComments, commentList);
    }

    @Override
    public boolean hasPrivilege(Integer id) {
        return userMapper.selectByPrimaryKey(id).getPrivilege() != -1;
    }
}
