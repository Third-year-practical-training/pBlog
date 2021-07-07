package com.pblogteam.pblog.vo;

import com.pblogteam.pblog.entity.Message;

import java.util.Date;

public class MessageVO extends Message {
    private String fromPhotoUrl;
    private String toPhotoUrl;

    public MessageVO(Message message, String fromPhotoUrl, String toPhotoUrl) {
        setId(message.getId());
        setFromId(message.getFromId());
        setFromName(message.getFromName());
        setToId(message.getToId());
        setToName(message.getToName());
        setDate(message.getDate());
        setContent(message.getContent());
        this.fromPhotoUrl = fromPhotoUrl;
        this.toPhotoUrl = toPhotoUrl;
    }
}
