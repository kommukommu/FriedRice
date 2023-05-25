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
        map.put("lastChange", article.getLastChange().toString());
        if (isOwnerOrWriter) {
            map.put("requirement", (null == article.getRequirement()) ? "" : article.getRequirement());
        }
        return map;
    }

    @GetMapping("/Article/Project/{project}")
    public Map<String, Object> getArticles(@PathVariable("project") Integer projectID) {
        Map<String, Object> map = new HashMap<>();

        List<Article> articles = articleService.getArticlesByProject(projectID);
        map.put("code", 0);
        map.put("message", "文章查询成功");
        map.put("articles", createArticleMap(articles));
        return map;
    }

    @GetMapping("/Article/Review/ID/{id}")
    public Map<String, Object> getReviewingArticle(@PathVariable("id") Integer articleID) {
        Map<String, Object> map = new HashMap<>();
        boolean isManager = false;
        Article article = articleService.getById(articleID);
        Project project = projectService.getById(article.getProject());
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer userID = Integer.parseInt(session.getAttribute("id").toString());
            if (project.getOwner().equals(userID)) isManager = true;
        }

        map.put("code", 0);
        map.put("message", "文章查询成功");
        map.put("article", createArticleMap(article, isManager));
        return map;
    }

    @GetMapping("/Article/ID/{id}")
    public Map<String, Object> getArticle(@PathVariable("id") Integer articleID) {
        Map<String, Object> map = new HashMap<>();
        boolean isManager = false;
        Article article = articleService.getById(articleID);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer userID = Integer.parseInt(session.getAttribute("id").toString());
            if (Objects.equals(article.getWriter(), userID)) isManager = true;
        }

        map.put("code", 0);
        map.put("message", "文章查询成功");
        map.put("article", createArticleMap(article, isManager));
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
        if (null == article.getWriter()) {
            map.put("code", -2);
            map.put("message", "文章未指定作者java");
            return map;
        }
        Project project = projectService.getById(article.getProject());
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -3);
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

    @PutMapping("/Article")
    public Map<String, Object> changeRequirement(@RequestBody Article article) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        Article originArticle = articleService.getById(article.getId());
        Project project = projectService.getById(originArticle.getProject());
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -2);
            map.put("message", "无法在非项目管理员的情况下修改文章");
            return map;
        }
        if (articleService.changeRequirement(article)) {
            map.put("code", 0);
            map.put("message", "文章修改成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }

    @PutMapping("/Article/Review")
    public Map<String, Object> passReview(@RequestBody Article article) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        article = articleService.getById(article.getId());
        Project project = projectService.getById(article.getProject());
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -2);
            map.put("message", "无法在非项目管理员的情况下修改审核状态");
            return map;
        }
        if (article.getState() == 1) {
            map.put("code", -3);
            map.put("message", "文章已通过审核");
            return map;
        }
        if (articleService.passReview(article)) {
            map.put("code", 0);
            map.put("message", "审核状态修改成功");
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
