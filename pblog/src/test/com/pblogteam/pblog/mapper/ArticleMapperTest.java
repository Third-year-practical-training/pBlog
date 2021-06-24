package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.Article;
import com.pblogteam.pblog.entity.ArticleExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleMapperTest {
    @Autowired
    private ArticleMapper articleMapper;
    @Test
    public void selectByUserIdTest() {
        Integer id = 1;
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria articleEx = articleExample.createCriteria();
        articleEx.andUserIdEqualTo(1);
        System.out.println(articleMapper.selectByExampleWithBLOBs(articleExample).get(0).getContent());
    }

    @Test
    public void deleteByArticleIdTest() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        Article article = new Article(null, null, 1000, "cscsc", date, (byte) 1, null, null, "sakcnscnskncncsncsncn");
        articleMapper.insertAndReturnPrimaryKey(article);
        System.out.println(article.getId());
    }

}