package com.pblogteam.pblog.entity;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer articleTypeId;

    private String title;

    private String summary;

    private Date date;

    private Byte published;

    private Integer collectionCount;

    private Integer commentCount;

    private Integer isFeature;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Integer articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getPublished() {
        return published;
    }

    public void setPublished(Byte published) {
        this.published = published;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getIsFeature() {
        return isFeature;
    }

    public void setIsFeature(Integer isFeature) {
        this.isFeature = isFeature;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", articleTypeId=").append(articleTypeId);
        sb.append(", title=").append(title);
        sb.append(", summary=").append(summary);
        sb.append(", date=").append(date);
        sb.append(", published=").append(published);
        sb.append(", collectionCount=").append(collectionCount);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", isFeature=").append(isFeature);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}