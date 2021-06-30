package com.pblogteam.pblog.vo;

import com.pblogteam.pblog.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {
    private Integer commentId;
    private Integer fromUserId;
    private String fromUserNickName;
    private Integer toUserId;
    private String toUserNickName;
    private String photo;
    private Date date;
    private String content;
    private Integer father;
    private List<CommentVO> childList;
}

