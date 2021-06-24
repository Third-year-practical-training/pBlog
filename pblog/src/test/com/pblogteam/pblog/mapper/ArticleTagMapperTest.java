package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.ArticleTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArticleTagMapperTest {
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Test
    public void insertAndReturnPrimaryKey() {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setName("sjsjsjsjsj");

        articleTagMapper.insertAndReturnPrimaryKey(articleTag);
        System.out.println(articleTag.getId());
    }
}