package com.pblogteam.pblog.service;

import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.entity.User;
import com.pblogteam.pblog.vo.UserNewVO;
import com.pblogteam.pblog.vo.UserVO;

import java.util.List;

public interface UserService {
    UserVO findByUserName(String username);

    boolean checkPassword(UserVO userVO, String password);

    UserVO findByUserId(Integer id, Integer signedinUserId);

    boolean signup(String username, String password, String email);

    PageInfo<UserVO> myAttentionList(Integer followerId, int pageNum);

    void changeAttention(Integer id, Integer signedinUserId);

    User selectByPrimaryKey(Integer id);

    void changePhoto(Integer userId, String photoUrl);

    boolean checkNewUsernameLegality(Integer userId, UserNewVO userNewVO);

    UserVO updateInfo(Integer userId, UserNewVO userNewVO);

    boolean isAdmin(Integer userId);

    void becomeAdmin(Integer userId);
}
