package com.friedrice.backendfriedrice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.friedrice.backendfriedrice.pojo.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public List<Article> getArticlesByProject (Integer projectID);
    public Long countArticlesByProject (Integer projectID);
    public Boolean addArticle(Article article);
    public Boolean swapArticles (List<Integer> ids);
    public Boolean changeRequirement (Article article);
    public Boolean passReview(Article article);
    public Boolean removeArticles (List<Integer> ids);
}
