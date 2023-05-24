package com.friedrice.backendfriedrice.controller;

import com.friedrice.backendfriedrice.pojo.Article;
import com.friedrice.backendfriedrice.pojo.Project;
import com.friedrice.backendfriedrice.service.ArticleService;
import com.friedrice.backendfriedrice.service.ProjectService;
import com.friedrice.backendfriedrice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ArticleController {
    private final HttpServletRequest request;
    private final Logger logger;

    private final ArticleService articleService;
    private final ProjectService projectService;
    private final UserService userService;

    public ArticleController(HttpServletRequest request, ArticleService articleService, ProjectService projectService, UserService userService) {
        this.request = request;
        this.articleService = articleService;
        this.projectService = projectService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public List<Map<String, Object>> createArticleMap(List<Article> articles) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Article item : articles) mapList.add(createArticleMap(item));
        return mapList;
    }

    public Map<String, Object> createArticleMap(Article article) {
        return createArticleMap(article, false);
    }

    public Map<String, Object> createArticleMap(Article article, boolean isOwnerOrWriter) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", article.getId());
        map.put("title", article.getTitle());
        map.put("project", article.getProject());
        map.put("writer", article.getWriter());
        map.put("writerName", userService.getUserNameByID(article.getWriter()));
        map.put("orderNumber", article.getOrderNumber());
        map.put("lastChange", article.getLastChange());
        if (isOwnerOrWriter) {
            map.put("requirement", article.getRequirement());
        }
        return map;
    }

    @GetMapping("/Article/Project/{project}")
    public Map<String, Object> getArticles(@PathVariable("project") Integer projectID) {
        Map<String, Object> map = new HashMap<>();

        List<Article> articles = articleService.getArticlesByProject(projectID);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("articles", createArticleMap(articles));
        return map;
    }

    @PostMapping("/Article")
    public Map<String, Object> saveArticle(@RequestBody Article article) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        Project project = projectService.getById(article.getProject());
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -2);
            map.put("message", "无法在非项目管理员的情况下创建文章");
            return map;
        }
        article.setState((short) 1);
        logger.info(String.valueOf(article));
        boolean result;
        try {
            result = articleService.addArticle(article);
        } catch (RuntimeException runtimeException) {
            map.put("code", -200);
            map.put("message", runtimeException);
            return map;
        }
        if (result) {
            map.put("code", 0);
            map.put("message", "文章添加成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }

    @PostMapping("/Article/Swap")
    public Map<String, Object> swapArticles(@RequestBody List<Integer> ids) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        if (ids.isEmpty()) {
            map.put("code", 0);
            map.put("message", "文章修改成功");
            return map;
        }
        Project project = projectService.getById(articleService.getById(ids.get(0)).getProject());
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -2);
            map.put("message", "无法在非项目管理员的情况下修改文章");
            return map;
        }
        boolean result;
        try {
            result = articleService.swapArticles(ids);
        } catch (RuntimeException runtimeException) {
            map.put("code", -200);
            map.put("message", runtimeException);
            return map;
        }
        if (result) {
            map.put("code", 0);
            map.put("message", "文章修改成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }

    @DeleteMapping("/Article")
    public Map<String, Object> removeArticles(@RequestBody List<Integer> ids) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        if (ids.isEmpty()) {
            map.put("code", 0);
            map.put("message", "文章删除成功");
            return map;
        }
        Project project = projectService.getById(articleService.getById(ids.get(0)).getProject());
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -2);
            map.put("message", "无法在非项目管理员的情况下删除文章");
            return map;
        }
        if (Boolean.TRUE.equals(articleService.removeArticles(ids))) {
            map.put("code", 0);
            map.put("message", "文章删除成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }
}
