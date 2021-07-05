package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.service.ArticleCollRelaService;
import com.pblogteam.pblog.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ArticleCollRelaServiceImplTest {
    @Autowired
    private ArticleCollRelaService articleService;
    @Test
    void getHotList() {
        System.out.println(articleService.getHotList());
    }
}