package com.pblogteam.pblog.service;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.entity.Message;


public interface MessageService {
    void addMessage(Message message);
    void deleteMessage(Integer id);
    PageInfo<Message> findMessage(Integer fromId, Integer toId, Integer pageNum);
}
