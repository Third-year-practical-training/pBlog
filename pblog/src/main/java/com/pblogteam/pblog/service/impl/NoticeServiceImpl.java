package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.Notice;
import com.pblogteam.pblog.entity.NoticeExample;
import com.pblogteam.pblog.mapper.NoticeMapper;
import com.pblogteam.pblog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void publicNotice(Notice notice) {
        noticeMapper.insertSelective(notice);
    }

    @Override
    public boolean deleteNotice(Integer id) {
        return noticeMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public PageInfo<Notice> getAllNotice(int pageNum) {
        NoticeExample example = new NoticeExample();
        NoticeExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(pageNum, Config.PAGE_SIZE, "date desc");
        List<Notice> notices = noticeMapper.selectByExampleWithBLOBs(example);
        return new PageInfo<>(notices);
    }
}
