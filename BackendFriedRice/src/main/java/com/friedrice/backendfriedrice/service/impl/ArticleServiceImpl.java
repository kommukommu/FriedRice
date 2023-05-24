package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.ArticleMapper;
import com.friedrice.backendfriedrice.pojo.Article;
import com.friedrice.backendfriedrice.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
