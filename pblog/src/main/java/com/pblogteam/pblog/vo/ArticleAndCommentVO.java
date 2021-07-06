package com.pblogteam.pblog.vo;


import com.pblogteam.pblog.entity.ArticleTag;
import com.pblogteam.pblog.entity.ArticleType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleAndCommentVO {
    private Integer id;
    private Integer userId;
    private String userNickname;
    private String title;
    private Date date;
    private String content;
    private Integer collectCount;
    private boolean myCollection;
    private boolean published;
    private ArticleType articleType;
    private List<ArticleTag> tagList;
    private List<CommentVO> commentList;
}


