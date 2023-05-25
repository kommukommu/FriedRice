package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.ArticleMapper;
import com.friedrice.backendfriedrice.mapper.BodyMapper;
import com.friedrice.backendfriedrice.pojo.Article;
import com.friedrice.backendfriedrice.pojo.Body;
import com.friedrice.backendfriedrice.service.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BodyServiceImpl extends ServiceImpl<BodyMapper, Body> implements BodyService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Boolean createNewBodiesByArticle(Integer articleID) {
        Body body1 = new Body();
        body1.setParent(articleID);
        body1.setBody("");
        body1.setIsLatest((short) 0);
        Body body2 = new Body();
        body2.setParent(articleID);
        body2.setBody("");
        body2.setIsLatest((short) 1);
        List<Body> bodies = new ArrayList<>();
        bodies.add(body1);
        bodies.add(body2);
        return this.saveBatch(bodies);
    }

    @Override
    public Body getBody(Integer articleID) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, articleID)
                .select(Article::getId, Article::getState);
        Article article = articleMapper.selectOne(queryWrapper);
        LambdaQueryWrapper<Body> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Body::getParent, article.getId())
                .eq(Body::getIsLatest, article.getState());
        return this.getOne(queryWrapper1);
    }

    @Override
    public Body getBody(Integer articleID, Integer articleState) {
        LambdaQueryWrapper<Body> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Body::getParent, articleID)
                .eq(Body::getIsLatest, articleState);
        return this.getOne(queryWrapper);
    }

    @Transactional
    @Override
    public Boolean updateBody(Integer articleID, Body body) throws RuntimeException {
        Integer earlierID = body.getId();
        body.setId(null);
        body.setParent(articleID);
        body.setIsLatest((short) 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, articleID)
                .select(Article::getId, Article::getState);
        Article article = articleMapper.selectOne(queryWrapper);
        LambdaQueryWrapper<Body> lambdaQueryWrapper = null;
        Short state = article.getState();
        if (state == 0) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Body::getParent, articleID)
                    .eq(Body::getIsLatest, 1);

            compareIDs(articleID, earlierID);
            this.remove(lambdaQueryWrapper);
            this.save(body);

        } else {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Body::getParent, articleID)
                    .eq(Body::getIsLatest, 0);
            LambdaUpdateWrapper<Body> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Body::getParent, articleID)
                    .eq(Body::getIsLatest, 1)
                    .set(Body::getIsLatest, 0);
            LambdaUpdateWrapper<Article> articleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            articleLambdaUpdateWrapper.eq(Article::getId, articleID)
                    .set(Article::getState, 0);
            compareIDs(articleID, earlierID);
            this.remove(lambdaQueryWrapper);
            compareIDs(articleID, earlierID);
            articleMapper.update(null, articleLambdaUpdateWrapper);
            compareIDs(articleID, earlierID);
            this.update(updateWrapper);
            this.save(body);
        }
        return true;
    }

    public void compareIDs(Integer articleID, Integer oldID) throws RuntimeException {
        LambdaQueryWrapper<Body> latestLambdaQueryWrapper = new LambdaQueryWrapper<>();
        latestLambdaQueryWrapper.select(Body::getId, Body::getParent)
                .eq(Body::getParent, articleID)
                .eq(Body::getIsLatest, 1);
        Body latest = this.getOne(latestLambdaQueryWrapper);
        Integer newID = latest.getId();
        if (newID != oldID) throw new RuntimeException("IDs do not match");
    }
}
