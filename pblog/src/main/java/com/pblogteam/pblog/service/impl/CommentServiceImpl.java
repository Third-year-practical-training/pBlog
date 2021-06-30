package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.Comment;
import com.pblogteam.pblog.entity.CommentExample;
import com.pblogteam.pblog.entity.User;
import com.pblogteam.pblog.mapper.CommentMapper;
import com.pblogteam.pblog.mapper.UserMapper;
import com.pblogteam.pblog.service.CommentService;
import com.pblogteam.pblog.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteCommentById(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentVO> selectByArticleId(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = null;
        criteria = commentExample.createCriteria();
        criteria.andArticleIdEqualTo(id);
        criteria.andReplyIdIsNull();
        List<Comment> fatherComment = commentMapper.selectByExampleWithBLOBs(commentExample);
        List<CommentVO> commentVOList = fillCommentVOByComment(fatherComment);
//        return commentMapper.selectByExampleWithBLOBs(commentExample);

        for(CommentVO commentVO: commentVOList) {
            criteria = commentExample.createCriteria();
            int fatherId = commentVO.getCommentId();
            criteria.andReplyIdEqualTo(fatherId);
            List<Comment> commentList = commentMapper.selectByExampleWithBLOBs(commentExample);
            while(commentList != null && !commentList.isEmpty()) {
                for(Comment comment: commentList) {
                    comment.setReplyId(fatherId);
                    commentVO.getChildList().add(castCommentToCommentVO(comment));
                }

            }
        }
        return null;
    }

    public CommentVO castCommentToCommentVO(Comment comment) {
        CommentVO commentVO = new CommentVO();
        commentVO.setCommentId(comment.getId());
        commentVO.setContent(comment.getContent());
        commentVO.setDate(comment.getDate());
        commentVO.setFromUserId(comment.getUserId());
        User fromUser = userMapper.selectByPrimaryKey(comment.getUserId());
        commentVO.setPhoto(fromUser.getPhotoUrl());
        commentVO.setFromUserNickName(fromUser.getNickname());
        commentVO.setFromUserId(fromUser.getId());
        commentVO.setChildList(null);
        // replyId改成fatherID
        if(comment.getReplyId() != null) {
            commentVO.setToUserId(comment.getReplyId());
            User toUser = userMapper.selectByPrimaryKey(comment.getReplyId());
            commentVO.setToUserNickName(toUser.getNickname());
        }
        return commentVO;
    }

    public List<CommentVO> fillCommentVOByComment(List<Comment> commentList) {
        List<CommentVO> commentVOList = new ArrayList<>();
        for(Comment comment: commentList) {
            commentVOList.add(castCommentToCommentVO(comment));
        }
        return commentVOList;
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
