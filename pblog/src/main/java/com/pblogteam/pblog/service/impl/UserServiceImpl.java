package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.constant.Privilege;
import com.pblogteam.pblog.entity.User;
import com.pblogteam.pblog.entity.UserExample;
import com.pblogteam.pblog.entity.UserFollowerRela;
import com.pblogteam.pblog.entity.UserFollowerRelaExample;
import com.pblogteam.pblog.mapper.UserFollowerRelaMapper;
import com.pblogteam.pblog.mapper.UserMapper;
import com.pblogteam.pblog.service.UserService;
import com.pblogteam.pblog.util.CopyPageInfo;
import com.pblogteam.pblog.util.FtpUtil;
import com.pblogteam.pblog.vo.UserNewVO;
import com.pblogteam.pblog.vo.UserVO;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserFollowerRelaMapper userFollowerRelaMapper;


    @Override
    public UserVO findByUserName(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null)
            return null;
        return createUserVOByUser(user);
    }


    @Override
    public boolean checkPassword(UserVO userVO, String password) {
        if (userVO.getPassword().equals(password))
            return true;
        else
            return false;
    }

    @Override
    public UserVO findByUserId(Integer id, Integer signedinUserId) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null)
            return null;
        else {
            UserVO userVO = createUserVOByUser(user);
            if (id != signedinUserId && signedinUserId != null) {
                if (userFollowerRelasExampleByIds(id, signedinUserId) != null)
                    userVO.setMyAttention(true);
            }
            return userVO;
        }
    }

    @Override
    public boolean signup(String username, String password, String email) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setNickname(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setSex((byte) 1);
            user.setPrivilege(0);
            userMapper.insert(user);
            return true;
        } else
            return false;
    }

    @Override
    public PageInfo<UserVO> myAttentionList(Integer followerId, int pageNum) {
        List<UserVO> attentionList = new ArrayList<>();
        PageHelper.startPage(pageNum, Config.PAGE_SIZE);
        List<Integer> attenionUserIds = userFollowerRelaMapper.selectByFollowerId(followerId);
        UserVO userVO = null;
        for (Integer i : attenionUserIds) {
            userVO = createUserVOByUser(userMapper.selectByPrimaryKey(i));
            userVO.setMyAttention(true);
            attentionList.add(userVO);
        }
        return CopyPageInfo.covertPageInfo(attentionList, attenionUserIds);
    }

    @Override
    public void changeAttention(Integer id, Integer signedinUserId) {
        UserFollowerRelaExample userFollowerRelaExample = userFollowerRelasExampleByIds(id, signedinUserId);
        if (userFollowerRelaExample != null) {
            userFollowerRelaMapper.deleteByExample(userFollowerRelaExample);
        } else {
            if (userMapper.selectByPrimaryKey(id) != null && userMapper.selectByPrimaryKey(signedinUserId) != null) {
                UserFollowerRela userFollowerRela = new UserFollowerRela();
                userFollowerRela.setUserId(id);
                userFollowerRela.setFollowerId(signedinUserId);
                userFollowerRelaMapper.insert(userFollowerRela);
            }
        }
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void getImageBuf(Integer id, HttpServletResponse response) {
        FTPClient ftp = null;
        InputStream in = null;
        OutputStream os = null;
        String imgPath = selectByPrimaryKey(id).getPhotoUrl();
        try {
            ftp = FtpUtil.initFTP(ftp);
            in = ftp.retrieveFileStream(new String(imgPath.getBytes("UTF-8"), "iso-8859-1"));
            String picType = imgPath.split("\\.")[1];
            BufferedImage bufImg = null;
            bufImg = ImageIO.read(in);
            os = response.getOutputStream();
            ImageIO.write(bufImg, picType, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    FtpUtil.destroy(ftp);
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public void changePhoto(Integer userId, String photoUrl) {
        User user = new User();
        user.setId(userId);
        user.setPhotoUrl(photoUrl);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean checkNewUsernameLegality(Integer userId, UserNewVO userNewVO) {
        User user = userMapper.selectByUsername(userNewVO.getUsername());
        //?????????????????????????????????????????????????????????????????????????????????
        return user == null || user.getId().equals(userId);
    }

    @Override
    public UserVO updateInfo(Integer userId, UserNewVO userNewVO) {
        User user = new User();
        user.setId(userId);
        user.setUsername(userNewVO.getUsername());
        user.setNickname(userNewVO.getNickname());
        user.setRealName(userNewVO.getReal_name());
        if (userNewVO.getSex().equals("???"))
            user.setSex((byte) 0);
        else
            user.setSex((byte) 1);
        user.setBirthday(userNewVO.getBirthday());
        user.setEduBg(userNewVO.getEdu_bg());
        user.setEmail(userNewVO.getEmail());
        user.setDescription(userNewVO.getDescription());
//        System.out.println(user);
        userMapper.updateByPrimaryKeySelective(user);
        return findByUserId(userId, userId);
    }

    @Override
    public boolean isAdmin(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user.getPrivilege() == 1;
    }

    @Override
    public void changeAdmin(Integer userId, Integer privilege) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPrivilege(privilege);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public PageInfo<UserVO> findAllUser(int pageNum) {
        UserExample userExample = new UserExample();
        PageHelper.startPage(pageNum, Config.USER_SIZE);
        List<User> userList = userMapper.selectByExample(userExample);
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : userList) {
            userVOS.add(createUserVOByUser(user));
        }
        return CopyPageInfo.covertPageInfo(userVOS, userList);
    }

    public static UserVO createUserVOByUser(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setPassword(user.getPassword());
        userVO.setReal_name(user.getRealName());
        userVO.setNickname(user.getNickname());
        userVO.setPhotoUrl(user.getPhotoUrl());
        userVO.setEmail(user.getEmail());
        if (user.getSex() == 0) {
            userVO.setSex("???");
        } else {
            userVO.setSex("???");
        }
        userVO.setBirthday(user.getBirthday());
        userVO.setEdu_bg(user.getEduBg());
        userVO.setPhotoUrl(user.getPhotoUrl());
        userVO.setDescription(user.getDescription());
        userVO.setAttentionCount(user.getAttentionCount() == null ? 0 : user.getAttentionCount());
        userVO.setMyAttention(false);
        userVO.setPrivilege(user.getPrivilege());
        return userVO;
    }

    public UserFollowerRelaExample userFollowerRelasExampleByIds(Integer id, Integer signedinUserId) {
        UserFollowerRelaExample userFollowerRelaExample = new UserFollowerRelaExample();
        UserFollowerRelaExample.Criteria criteria = userFollowerRelaExample.createCriteria();
        criteria.andFollowerIdEqualTo(signedinUserId);
        criteria.andUserIdEqualTo(id);
        List<UserFollowerRela> list = userFollowerRelaMapper.selectByExample(userFollowerRelaExample);
        if (list.isEmpty())
            return null;
        else
            return userFollowerRelaExample;
    }

}
