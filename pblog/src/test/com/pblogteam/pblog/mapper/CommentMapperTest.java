package com.pblogteam.pblog.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class CommentMapperTest {
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void ascasc() {
        System.out.println(commentMapper.selectByPrimaryKey(4));
    }
}