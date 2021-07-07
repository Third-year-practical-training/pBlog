package com.pblogteam.pblog.vo;

import com.pblogteam.pblog.entity.Comment;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyComment {
    private Integer articleId;
    private String articleTitle;
    private Integer commentId;
    private Date date;
    private Integer toUserId;
    private String toUserNickname;
    private String content;

    public MyComment(Comment comment, String articleTitle) {
        this.articleId = comment.getArticleId();
        this.commentId = comment.getId();
        this.articleTitle = articleTitle;
        this.date = comment.getDate();
        this.toUserId = comment.getToId();
        this.toUserNickname = comment.getToName();
        this.content = comment.getContent();
    }
}
