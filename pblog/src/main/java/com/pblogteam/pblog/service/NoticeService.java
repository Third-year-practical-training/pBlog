package com.pblogteam.pblog.service;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.entity.Notice;


public interface NoticeService {
    void publicNotice(Notice notice);
    boolean deleteNotice(Integer id);
    PageInfo<Notice> getAllNotice(int pageNum);
}
