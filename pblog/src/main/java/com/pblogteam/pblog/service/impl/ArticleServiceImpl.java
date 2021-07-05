package com.pblogteam.pblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.HanLP;
import com.pblogteam.pblog.config.Config;
import com.pblogteam.pblog.entity.*;
import com.pblogteam.pblog.mapper.ArticleMapper;
import com.pblogteam.pblog.service.ArticleService;
import com.pblogteam.pblog.util.CopyPageInfo;
import com.pblogteam.pblog.vo.ArticleAndCommentVO;
import com.pblogteam.pblog.vo.ArticleNewVO;
import com.pblogteam.pblog.vo.ArticleTitleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.pblogteam.pblog.util.CopyPageInfo.covertPageInfo;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagServiceImpl tagServiceImpl;
    @Autowired
    private ArticleTagRelaServiceImpl articleTagRelaServiceImpl;
    @Autowired
    private ArticleCollRelaServiceImpl articleCollRelaServiceImpl;
    @Autowired
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    private TypeServiceImpl typeServiceImpl;


    private static final Integer ARTICLE_SUMMARY_LENGTH = 50;

    // 查询文章列表

    /**
     *
     * @param id   userId
     * @return     需要的列表信息
     */
    @Override
    public PageInfo<ArticleTitleVO> selectArtOrDraListByUserId(Integer id, int flag, int pageNum) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria articleEx = articleExample.createCriteria();
        articleEx.andUserIdEqualTo(id);
        articleEx.andPublishedEqualTo((byte) flag);
        return pageHelperSelect(pageNum, articleExample);
    }

    public PageInfo<ArticleTitleVO> pageHelperSelect(int pageNum, ArticleExample articleExample) {
        PageHelper.startPage(pageNum, Config.PAGE_SIZE, "date desc");
        List<Article> articleList = articleMapper.selectByExampleWithBLOBs(articleExample);
        return covertPageInfo(fillArtTitVOByArtList(articleList), articleList);
    }

    @Override
    public PageInfo<ArticleTitleVO> showAllArticle(int pageNum) {
        ArticleExample articleExample = new ArticleExample();
        return pageHelperSelect(pageNum, articleExample);
    }

    @Override
    public PageInfo<ArticleTitleVO> selectByTypeId(Integer id, int pageNum) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria articleEx = articleExample.createCriteria();
        articleEx.andArticleTypeIdEqualTo(id);
        articleEx.andPublishedEqualTo((byte) 1);
        return pageHelperSelect(pageNum, articleExample);
    }

    public List<ArticleTitleVO> fillArtTitVOByArtList(List<Article> articleList) {
        List<ArticleTitleVO> articleTitleVOList = new ArrayList<>();
        if(articleList != null) {
            for (Article a :
                    articleList) {
                ArticleTitleVO articleTitleVO = new ArticleTitleVO();
                articleTitleVO.setId(a.getId());
                articleTitleVO.setUserId(a.getUserId());
                articleTitleVO.setIsFeature(a.getIsFeature() == 1);
                // 查nickname
                articleTitleVO.setUserNickname(userServiceImpl.selectByPrimaryKey(a.getUserId()).getNickname());
                articleTitleVO.setTitle(a.getTitle());
                articleTitleVO.setDate(a.getDate());
                // 查文章简介
                articleTitleVO.setSummary(a.getSummary());
                articleTitleVO.setCollectCount(a.getCollectionCount());
                articleTitleVO.setCommentCount(a.getCommentCount());

                articleTitleVO.setArticleTagList(selectTagListByArticleId(a.getId()));
                articleTitleVOList.add(articleTitleVO);
            }
        }
        return articleTitleVOList;
    }

    public List<ArticleTag> selectTagListByArticleId(Integer id) {
        List<ArticleTagRela> articleTagRelaList = articleTagRelaServiceImpl.selectByArticleId(id);
        List<ArticleTag> articleTagList = new ArrayList<>();
        if(articleTagRelaList != null) {
            for (ArticleTagRela a :
                    articleTagRelaList) {
                articleTagList.add(tagServiceImpl.selectByPrimaryKey(a.getTagId()));
            }
        }
        return articleTagList;
    }

    /**
     *
     * @param id  要删除的文章的Id
     * @return  true 成功
     *          false 失败
     */
    @Override
    public boolean deleteArticleById(Integer id) {
        // 删文章表
        boolean published = articleMapper.selectByPrimaryKey(id).getPublished() != 0x00;
        int flag = articleMapper.deleteByPrimaryKey(id);
        if(flag == 0) return false;
        // 删评论表
        if(published) {
            commentServiceImpl.deleteByArticleId(id);
            // 删收藏
            articleCollRelaServiceImpl.deleteByArticleId(id);
        }
        // 删tag
        articleTagRelaServiceImpl.deleteByArticleId(id);
        return true;
    }

    @Override
    public void addAndUpdate(ArticleNewVO articleNewVO, Byte published) {
        Article article = new Article();
        List<ArticleTag> tagList = articleNewVO.getTag();
        article.setUserId(articleNewVO.getUserId());
        article.setArticleTypeId(articleNewVO.getArticleTypeId());
        article.setTitle(articleNewVO.getTitle());
        article.setDate(articleNewVO.getDate());
        article.setContent(articleNewVO.getContent());
        article.setId(articleNewVO.getId());
        article.setSummary(articleNewVO.getSummary());
        // 添加文章
        if(article.getId() == null) {
            // 添加新文章
            article.setPublished(published);
            article.setCollectionCount(0);
            article.setCommentCount(0);
            articleMapper.insertAndReturnPrimaryKey(article);
            if(tagList != null) {
                for (ArticleTag a :
                        tagList) {
                    // 查找是否存在该 tag
                    List<ArticleTag> articleTagList = tagServiceImpl.selectByTagName(a.getName());
                    ArticleTag articleTag = new ArticleTag();
                    ArticleTagRela articleTagRela = new ArticleTagRela();

                    articleTag.setName(a.getName());
                    if(articleTagList.size() == 0) {
                        // 不存在
                        tagServiceImpl.insertAndReturnPrimaryKey(articleTag);
                    } else {
                        // 存在
                        articleTag = articleTagList.get(0);
                    }
                    articleTagRela.setArticleId(article.getId());
                    articleTagRela.setTagId(articleTag.getId());
                    articleTagRelaServiceImpl.insert(articleTagRela);
                }
            }
        } else {
            // 修改文章
            articleMapper.updateByPrimaryKeySelective(article);
            articleTagRelaServiceImpl.deleteByArticleId(article.getId());
            if(tagList != null) {
                for (ArticleTag a :
                        tagList) {
                    tagServiceImpl.insertAndReturnPrimaryKey(a);
                    articleTagRelaServiceImpl.insert(new ArticleTagRela(article.getId(), a.getId()));
                }
            }
        }
    }

    @Override
    public ArticleAndCommentVO selectByArticleId(Integer id, Integer curUserId) {
        Article article = articleMapper.selectByPrimaryKey(id);
        ArticleAndCommentVO articleAndCommentVO = new ArticleAndCommentVO();
        articleAndCommentVO.setId(article.getId());
        articleAndCommentVO.setUserId(article.getUserId());
        articleAndCommentVO.setUserNickname(userServiceImpl.selectByPrimaryKey(article.getUserId()).getNickname());
        articleAndCommentVO.setTitle(article.getTitle());
        articleAndCommentVO.setDate(article.getDate());
        articleAndCommentVO.setContent(article.getContent());
        articleAndCommentVO.setCollectCount(article.getCollectionCount());
        articleAndCommentVO.setMyCollection(articleCollRelaServiceImpl.isExist(new ArticleCollectorRela(curUserId, article.getId())));
        articleAndCommentVO.setPublished(article.getPublished() != 0x00);
        articleAndCommentVO.setTagList(selectTagListByArticleId(article.getId()));
        articleAndCommentVO.setArticleType(typeServiceImpl.findTypeById(article.getArticleTypeId()));
        articleAndCommentVO.setCommentList(commentServiceImpl.selectByArticleId(id));
        return articleAndCommentVO;
    }

    @Override
    public PageInfo<ArticleTitleVO> selectCollectListByUserId(Integer id, int pageNum) {
        ArticleExample articleExample = new ArticleExample();
        List<Article> articleList = new ArrayList<>();
        List<ArticleCollectorRela> artCollRelaList = null;
        PageHelper.startPage(pageNum, Config.PAGE_SIZE);
        artCollRelaList = articleCollRelaServiceImpl.selectByUserId(id);
        if(artCollRelaList != null) {
            for (ArticleCollectorRela a :
                    artCollRelaList) {
                articleList.add(articleMapper.selectByPrimaryKey(a.getArticleId()));
            }
        }
        return covertPageInfo(fillArtTitVOByArtList(articleList), artCollRelaList);
    }

    @Override
    public void changeCollection(Integer userId, Integer articleId) {
        articleCollRelaServiceImpl.changeCollStatus(new ArticleCollectorRela(userId, articleId));
        Article article = articleMapper.selectByPrimaryKey(articleId);
        article.setCollectionCount(article.getCollectionCount() + 1);
        articleMapper.updateByPrimaryKey(article);
    }

    /**
     *
     * @param id
     * @param curId 传入用户ID，判断是否是自己的文章，不需要判断时传入-1
     * @return
     */
    @Override
    public boolean isArticle(Integer id, Integer curId) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article.getPublished() == 0x01 && (curId == -1 || article.getUserId().equals(curId));
    }

    @Override
    public void publishDraft(Integer id) {
        Article article = new Article();
        article.setId(id);
        article.setPublished((byte) 1);
        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public void changeFeature(Integer id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setIsFeature(article.getIsFeature() == 1 ? 0 : 1);
        articleMapper.updateByPrimaryKey(article);
    }

    @Override
    public PageInfo<ArticleTitleVO> selectArticleByKeyWord(String keyWord, int type, int id, int pageNum) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andTitleLike("%" + keyWord + "%");
        if(type == 1) {
            criteria.andArticleTypeIdEqualTo(id);
        } else if(type == 2) {
            criteria.andIdIn(articleTagRelaServiceImpl.selectArticleIdsByTagId(id));
        }
        long cnt = articleMapper.countByExample(articleExample);
        List<Article> articleList = null;
        if(cnt == 0) {
            // 提取短语查询
            List<String> termList = HanLP.extractPhrase(keyWord, 1);
            if(termList.size() == 0) {
                termList = HanLP.extractKeyword(keyWord, keyWord.length() / 2);
            }
            // 拼接正则字符串
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < termList.size(); i++) {
                String word = termList.get(i);
                if (i != termList.size() - 1) {
                    sb.append(word + "|");
                } else {
                    sb.append(word);
                }
            }
            String regex = sb.toString();
            sb.insert(0, ".*(");
            sb.append(").*");
            PageHelper.startPage(pageNum, Config.PAGE_SIZE, "date desc");
            articleList = articleMapper.selectByTitleKeyWord(sb.toString());
            articleList.forEach(article -> {
                String title = article.getTitle();
                // 利用正则替换
                String newTitle = title.replaceAll(regex, "<font style='color:red;'>$0</font>");
                article.setTitle(newTitle);
            });
        } else {
//            直接查询
            PageHelper.startPage(pageNum, Config.PAGE_SIZE, "date desc");
            articleList = articleMapper.selectByExampleWithBLOBs(articleExample);
            articleList.forEach(article -> {
                String title = article.getTitle();
                // 利用正则替换
                String newTitle = title.replaceAll(keyWord, "<font style='color:red;'>$0</font>");
                article.setTitle(newTitle);
            });
        }
        return covertPageInfo(fillArtTitVOByArtList(articleList), articleList);
    }
}
