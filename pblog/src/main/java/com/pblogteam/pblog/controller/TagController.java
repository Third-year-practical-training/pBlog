package com.pblogteam.pblog.controller;

import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.entity.ArticleTag;
import com.pblogteam.pblog.service.ArticleTagRelaService;
import com.pblogteam.pblog.service.TagService;
import com.pblogteam.pblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TagController {
    @Autowired
    private TagService tagService;

    @Cacheable(value = "hotTag")
    @GetMapping("/getHotTags")
    public ResultVO<List<ArticleTag>> getHotTags() {
        return ResultVO.throwSuccessAndData(ResponseState.SUCCESS, tagService.getHotTag());
    }
}
