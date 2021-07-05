package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.Message;
import com.pblogteam.pblog.entity.MessageExample;
import com.pblogteam.pblog.mapper.MessageMapper;
import com.pblogteam.pblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void addMessage(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Message> findMessage(Integer fromId, Integer toId, Integer pageNum) {
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andFromIdEqualTo(fromId);
        criteria.andToIdEqualTo(toId);
        PageHelper.startPage(pageNum, Config.MESSAGE_SIZE);
        return new PageInfo<>(messageMapper.selectByExampleWithBLOBs(messageExample));
    }
}
