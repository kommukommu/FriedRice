package com.friedrice.backendfriedrice.controller;

import com.friedrice.backendfriedrice.pojo.Body;
import com.friedrice.backendfriedrice.service.ArticleService;
import com.friedrice.backendfriedrice.service.BodyService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BodyController {
    private final HttpServletRequest request;
    private final Logger logger;
    private final ArticleService articleService;
    private final BodyService bodyService;

    public BodyController(HttpServletRequest request, ArticleService articleService, BodyService bodyService) {
        this.request = request;
        this.articleService = articleService;
        this.bodyService = bodyService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public Map<String, Object> createBodyMap(Body body) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", body.getId());
        map.put("parent", body.getParent());
        map.put("body", (null == body.getBody()) ? "" : body.getBody());
        return map;
    }

    @GetMapping("/Body/{id}")
    public Map<String, Object> getArticleBody(@PathVariable("id") Integer articleID) {
        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("message", "文章正文查询成功");
        map.put("body", createBodyMap(bodyService.getBody(articleID)));
        return map;
    }

    @GetMapping("/Body/Latest/{id}")
    public Map<String, Object> getLatestArticleBody(@PathVariable("id") Integer articleID) {
        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("message", "文章正文查询成功");
        map.put("body", createBodyMap(bodyService.getBody(articleID, 1)));
        return map;
    }

    @GetMapping("/Body/Earlier/{id}")
    public Map<String, Object> getEarlierArticleBody(@PathVariable("id") Integer articleID) {
        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("message", "文章正文查询成功");
        map.put("body", createBodyMap(bodyService.getBody(articleID, 0)));
        return map;
    }



//    @GetMapping("/Body/id")
//    public Map<String, Object> getArticleBody(@PathVariable("id") Integer articleID) {
//        Map<String, Object> map = new HashMap<>();
//        boolean isManager = false;
//        Article article = articleService.getById(articleID);
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Integer userID = Integer.parseInt(session.getAttribute("id").toString());
//            if (Objects.equals(article.getWriter(), userID)) isManager = true;
//        }
//
//        map.put("code", 0);
//        map.put("message", "文章查询成功");
//        map.put("article", createArticleMap(article, isManager));
//        return map;
//    }
}
