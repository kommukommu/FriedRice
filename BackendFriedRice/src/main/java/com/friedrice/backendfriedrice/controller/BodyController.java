package com.friedrice.backendfriedrice.controller;

import com.friedrice.backendfriedrice.pojo.Article;
import com.friedrice.backendfriedrice.pojo.Body;
import com.friedrice.backendfriedrice.pojo.Project;
import com.friedrice.backendfriedrice.service.ArticleService;
import com.friedrice.backendfriedrice.service.BodyService;
import com.friedrice.backendfriedrice.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BodyController {
    private final HttpServletRequest request;
    private final Logger logger;
    private final ProjectService projectService;
    private final ArticleService articleService;
    private final BodyService bodyService;

    public BodyController(HttpServletRequest request, ProjectService projectService, ArticleService articleService, BodyService bodyService) {
        this.request = request;
        this.projectService = projectService;
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
        HttpSession session = request.getSession(false);
        Article article = articleService.getById(articleID);
        boolean isManager = false;
        if (session != null) {
            Integer userID = Integer.parseInt(session.getAttribute("id").toString());
            if (article.getWriter().equals(userID)) isManager = true;
            else {
                Project project = projectService.getById(article.getProject());
                if (project.getOwner().equals(userID)) isManager = true;
            }
        }

        if (isManager) {
            map.put("code", 0);
            map.put("message", "文章正文查询成功");
            map.put("body", createBodyMap(bodyService.getBody(articleID, 1)));
            return map;
        }
        map.put("code", -1);
        map.put("message", "文章正文查询失败");
        return map;
    }

    @GetMapping("/Body/Earlier/{id}")
    public Map<String, Object> getEarlierArticleBody(@PathVariable("id") Integer articleID) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        Article article = articleService.getById(articleID);
        boolean isManager = false;
        if (session != null) {
            Integer userID = Integer.parseInt(session.getAttribute("id").toString());
            if (article.getWriter().equals(userID)) isManager = true;
            else {
                Project project = projectService.getById(article.getProject());
                if (project.getOwner().equals(userID)) isManager = true;
            }
        }
        if (isManager) {
            map.put("code", 0);
            map.put("message", "文章正文查询成功");
            map.put("body", createBodyMap(bodyService.getBody(articleID, 0)));
            return map;
        }

        map.put("code", -1);
        map.put("message", "文章正文查询失败");
        return map;
    }


    @PutMapping("/Body/{id}")
    public Map<String, Object> changeArticleBody(@PathVariable("id") Integer articleID
            , @RequestBody Body body) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        Article article = articleService.getById(articleID);
        boolean isManager = false;
        if (session != null) {
            Integer userID = Integer.parseInt(session.getAttribute("id").toString());
            if (article.getWriter().equals(userID)) isManager = true;
        }
        if (isManager) {
            boolean result;
            try {
                result = bodyService.updateBody(articleID, body);
            } catch (RuntimeException runtimeException) {
                map.put("code", -200);
                map.put("message", runtimeException);
                return map;
            }
            if (result) {
                map.put("code", 0);
                map.put("message", "文章正文修改成功");
                return map;
            }
            map.put("code", -100);
            map.put("message", "文章正文修改失败");
            return map;
        }

        map.put("code", -1);
        map.put("message", "无修改文章正文权限");
        return map;
    }
}
