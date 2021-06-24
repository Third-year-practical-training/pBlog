package com.pblogteam.pblog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    private Integer commentId;
    private Integer userId;
    private String userNickname;
    private Date date;
    private String content;
}
