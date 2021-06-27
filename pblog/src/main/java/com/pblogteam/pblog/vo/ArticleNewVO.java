package com.pblogteam.pblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pblogteam.pblog.entity.ArticleTag;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ArticleNewVO {
    private Integer id;
    private Integer userId;
    private Integer articleTypeId;
    private String title;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Shanghai")
    private Date date;
    private String content;
    private List<ArticleTag> tag;
}
