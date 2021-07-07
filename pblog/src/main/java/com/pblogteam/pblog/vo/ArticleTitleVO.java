package com.pblogteam.pblog.vo;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.pblogteam.pblog.entity.ArticleTag;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleTitleVO {
    private Integer id;
    private Integer userId;
    private String userNickname;
    private String photoUrl;
    private String title;
    private Date date;
    private String summary;
    private Integer collectCount;
    private Boolean isFeature;
    private Integer commentCount;
    private List<ArticleTag> articleTagList;
}
