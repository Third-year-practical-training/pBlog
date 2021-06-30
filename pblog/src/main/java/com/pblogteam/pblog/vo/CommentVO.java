package com.pblogteam.pblog.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {
    private Integer commentId;
    private Integer userId;
    private String photo;
    private String userNickname;
    private Date date;
    private String content;
    private Integer father;
    private List<CommentChild> childList;
}
@Data
class CommentChild {
    private Integer commentId;
    private Integer userId;
    private String photo;
    private String userNickname;
    private Date date;
    private String content;
    private Integer father;
}
