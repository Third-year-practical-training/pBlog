package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.ArticleTagRela;
import com.pblogteam.pblog.entity.ArticleTagRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleTagRelaMapper {
    // my own start
    List<ArticleTagRela> getHotTagList();
    // my own end

    int countByExample(ArticleTagRelaExample example);

    int deleteByExample(ArticleTagRelaExample example);

    int insert(ArticleTagRela record);

    int insertSelective(ArticleTagRela record);

    List<ArticleTagRela> selectByExample(ArticleTagRelaExample example);

    int updateByExampleSelective(@Param("record") ArticleTagRela record, @Param("example") ArticleTagRelaExample example);

    int updateByExample(@Param("record") ArticleTagRela record, @Param("example") ArticleTagRelaExample example);
}