package com.pblogteam.pblog.entity;

import java.io.Serializable;

public class AdminBan implements Serializable {
    private Integer id;

    private Integer bannedId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBannedId() {
        return bannedId;
    }

    public void setBannedId(Integer bannedId) {
        this.bannedId = bannedId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bannedId=").append(bannedId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}