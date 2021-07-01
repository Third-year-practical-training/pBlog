package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.service.UserService;
import com.pblogteam.pblog.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class UserServiceImplTest
{

    @Autowired
    UserService userService;
    @Test
    void findByUserName()
    {
        System.out.println(userService.findByUserName("posttest"));
    }

    @Test
    void checkPassword()
    {
        UserVO userVO = userService.findByUserName("test");
        System.out.println(userService.checkPassword(userVO, "123456"));
        System.out.println(userService.checkPassword(userVO, ""));
    }

    @Test
    void signup()
    {
        System.out.println(userService.signup("test", "123", "123@qq.com"));
        System.out.println(userService.signup("Natsuki", "合法萝莉", "147@qq.com"));
    }


//    void myAttentionList(int id)
//    {
//        List<UserVO> attentions = userService.myAttentionList(id);
//        for (UserVO uVO : attentions)
//            System.out.println(uVO.getUsername());
//    }

//    @Test
//    void changeAttention()
//    {
//        myAttentionList(2);
//        userService.changeAttention(3, 2);
//        myAttentionList(2);
//    }
}