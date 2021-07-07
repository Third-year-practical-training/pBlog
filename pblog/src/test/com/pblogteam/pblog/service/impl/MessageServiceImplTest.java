package com.pblogteam.pblog.service.impl;

import com.pblogteam.pblog.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceImplTest {
    @Autowired
    private MessageService messageService;

    @Test
    public void test() {
        System.out.println(messageService.findMessage(15, 19, 1));
    }
}