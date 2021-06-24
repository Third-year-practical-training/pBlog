package com.pblogteam.pblog.vo;

import com.pblogteam.pblog.entity.ArticleTag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleTitleVO {
    private Integer id;
    private Integer userId;
    private String userNickname;
    private String title;
    private Date date;
    private String summary;
    private Integer collectCount;
    private Integer commentCount;
    private List<ArticleTag> articleTagList;
}
