package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.entity.BlackList;
import com.pblogteam.pblog.entity.BlackListExample;
import com.pblogteam.pblog.mapper.BlackListMapper;
import com.pblogteam.pblog.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackListServiceImpl implements BlackListService {

    @Autowired
    private BlackListMapper blackListMapper;

    @Override
    public boolean isInMyBlackList(Integer myId, Integer id) {
        BlackListExample blackListExample = new BlackListExample();
        BlackListExample.Criteria criteria = blackListExample.createCriteria();
        criteria.andUserIdEqualTo(myId);
        criteria.andBannedIdEqualTo(id);
        return blackListMapper.selectByExample(blackListExample).size() != 0;
    }

    @Override
    public void addMyBlackList(Integer myId, Integer id) {
        BlackList blackList = new BlackList();
        blackList.setUserId(myId);
        blackList.setBannedId(id);
        blackListMapper.insertSelective(blackList);
    }

    @Override
    public void deleteFromMyBlackList(Integer myId, Integer id) {
        BlackListExample blackListExample = new BlackListExample();
        BlackListExample.Criteria criteria = blackListExample.createCriteria();
        criteria.andUserIdEqualTo(myId);
        criteria.andBannedIdEqualTo(id);
        blackListMapper.deleteByExample(blackListExample);
    }
}
