package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.ArticleMapper;
import com.friedrice.backendfriedrice.pojo.Article;
import com.friedrice.backendfriedrice.service.ArticleService;
import com.friedrice.backendfriedrice.service.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    BodyService bodyService;

    @Override
    public List<Article> getArticlesByProject(Integer projectID) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getProject, projectID).orderByAsc(Article::getOrderNumber);
        return this.list(queryWrapper);
    }

    @Override
    public Long countArticlesByProject(Integer projectID) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getProject, projectID);
        return this.count(queryWrapper);
    }

    @Transactional
    @Override
    public Boolean addArticle(Article article) throws RuntimeException {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getProject, article.getProject())
                .orderByDesc(Article::getOrderNumber);
        List<Article> list = this.list(queryWrapper);
        int order = 0;
        if (!list.isEmpty()) {
            order = list.get(0).getOrderNumber();
        }

        article.setOrderNumber(order + 1);
//        log.warn(article.toString());
        if (!this.save(article)) throw new RuntimeException("ArticleService");
        if (!bodyService.createNewBodiesByArticle(article.getId())) {
            throw new RuntimeException("BodyService");
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean swapArticles(List<Integer> ids) throws RuntimeException {
        if (ids.isEmpty()) return true;
        if (ids.size() > 2) return false;

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getId, ids).select(Article::getId, Article::getOrderNumber);
        List<Article> articleList = this.list(queryWrapper);
        int summary = 0;
        for (Article article : articleList) summary += article.getOrderNumber();

        LambdaUpdateWrapper<Article> updateWrapper1 = new LambdaUpdateWrapper<>();
        updateWrapper1.eq(Article::getId, articleList.get(0).getId())
                .set(Article::getOrderNumber, summary - articleList.get(0).getOrderNumber());

        LambdaUpdateWrapper<Article> updateWrapper2 = new LambdaUpdateWrapper<>();
        updateWrapper2.eq(Article::getId, articleList.get(1).getId())
                .set(Article::getOrderNumber, summary - articleList.get(1).getOrderNumber());
        if (!this.update(updateWrapper1)) throw new RuntimeException("updateWrapper1");
        if (!this.update(updateWrapper2)) throw new RuntimeException("updateWrapper2");
        return true;
    }

    @Override
    public Boolean removeArticles(List<Integer> ids) {
        if (ids.isEmpty()) return true;
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getId, ids);
        return this.remove(queryWrapper);
    }
}
