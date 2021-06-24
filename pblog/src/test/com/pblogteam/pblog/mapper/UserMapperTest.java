package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.User;
import com.pblogteam.pblog.entity.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void exampleTest() {
        UserExample example = new UserExample();
        UserExample.Criteria ex = example.createCriteria();
        ex.andIdGreaterThan(1);
        List<User> list = userMapper.selectByExample(example);
        for (User s :
                list) {
            System.out.println(s.getUsername());
        }
        System.out.println("----------------------------");
        ex.andIdLessThan(11);
        list = userMapper.selectByExample(example);
        for (User s :
                list) {
            System.out.println(s.getUsername());
        }
    }
}