package com.pblogteam.pblog.service;

public interface BlackListService {
    boolean isInMyBlackList(Integer myId, Integer id);
    void addMyBlackList(Integer myId, Integer id);
    void deleteFromMyBlackList(Integer myId, Integer id);
}
