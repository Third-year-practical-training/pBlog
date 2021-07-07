package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.Message;
import com.pblogteam.pblog.entity.MessageExample;
import com.pblogteam.pblog.mapper.MessageMapper;
import com.pblogteam.pblog.service.MessageService;
import com.pblogteam.pblog.service.UserService;
import com.pblogteam.pblog.util.CopyPageInfo;
import com.pblogteam.pblog.vo.MessageVO;
import com.pblogteam.pblog.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Override
    public void addMessage(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<MessageVO> findMessage(Integer fromId, Integer toId, Integer pageNum) {
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria1 = messageExample.createCriteria();
        criteria1.andFromIdEqualTo(fromId);
        criteria1.andToIdEqualTo(toId);
        MessageExample.Criteria criteria2 = messageExample.createCriteria();
        criteria2.andFromIdEqualTo(toId);
        criteria2.andToIdEqualTo(fromId);
        messageExample.or(criteria2);
        PageHelper.startPage(pageNum, Config.MESSAGE_SIZE);
        List<Message> messages = messageMapper.selectByExampleWithBLOBs(messageExample);
        List<MessageVO> messageVOS = new ArrayList<>();
        String fromUrl = userService.selectByPrimaryKey(fromId).getPhotoUrl();
        String toUrl = userService.selectByPrimaryKey(toId).getPhotoUrl();
        for(Message message:messages) {
            messageVOS.add(new MessageVO(message, fromUrl, toUrl));
        }
        return CopyPageInfo.covertPageInfo(messageVOS, messages);
    }

    @Override
    public List<UserVO> findMyUser(Integer id) {
        List<Integer> userIdList = messageMapper.findMyUser(id);
        List<UserVO> userVOS = new ArrayList<>();
        for(Integer userId: userIdList) {
            userVOS.add(userService.findByUserId(userId, id));
        }
        return userVOS;
    }
}
