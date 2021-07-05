package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class TagServiceImplTest {
    @Autowired
    private TagService tagService;
    @Test
    void getHotTag() {
        System.out.println(tagService.getHotTag());
    }
}