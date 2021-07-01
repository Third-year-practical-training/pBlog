package com.pblogteam.pblog.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ArticleTagRelaMapperTest {
    @Autowired
    private ArticleTagRelaMapper articleTagRelaMapper;

    @Test
    void getHotTagList() {
        System.out.println(articleTagRelaMapper.getHotTagList());
    }
}