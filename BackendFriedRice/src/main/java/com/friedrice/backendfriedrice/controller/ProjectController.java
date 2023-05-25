package com.friedrice.backendfriedrice.controller;

import com.friedrice.backendfriedrice.pojo.Project;
import com.friedrice.backendfriedrice.service.ProjectService;
import com.friedrice.backendfriedrice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class ProjectController {
    private final Logger logger;
    private final ProjectService projectService;
    private final UserService userService;
    private final HttpServletRequest request;

    public ProjectController(ProjectService projectService, UserService userService, HttpServletRequest request) {
        this.projectService = projectService;
        this.userService = userService;
        this.request = request;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public List<Map<String, Object>> convertUserID(List<Project> projects) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        projects.forEach(item -> mapList.add(convertUserID(item)));
        return mapList;
    }

    public Map<String, Object> convertUserID(Project project) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", project.getId());
        map.put("name", project.getName());
        map.put("description", project.getDescription());
        map.put("createDate", project.getCreateDate());
        map.put("owner", project.getOwner());
        map.put("ownerName", userService.getUserNameByID(project.getOwner()));
        return map;
    }

    @GetMapping("/Project")
    public Map<String, Object> getProjectsByName() {
        Map<String, Object> map = new HashMap<>();
        List<Project> projects = projectService.getProjectsByNameLike(null);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", convertUserID(projects));
        return map;
    }

    @GetMapping("/Project/ID/{id}")
    public Map<String, Object> getProjectByID(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Project project = projectService.getById(id);
        if (project == null) {
            map.put("code", -1);
            map.put("message", "无法找到该项目");
            return map;
        }
        map.put("code", 0);
        map.put("message", "项目详情查询成功");
        map.put("project", convertUserID(project));
        return map;
    }

    @GetMapping("/Project/Name/{name}")
    public Map<String, Object> getProjectsByName(@PathVariable("name") String name) {
        Map<String, Object> map = new HashMap<>();
        List<Project> projects = projectService.getProjectsByNameLike(name);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", convertUserID(projects));
        return map;
    }

    @GetMapping("/Project/Owner/{owner}")
    public Map<String, Object> getProjectsByOwner(@PathVariable("owner") Integer owner) {
        Map<String, Object> map = new HashMap<>();
        List<Project> projects = projectService.getProjectsByOwnerList(owner);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", convertUserID(projects));
        return map;
    }

    @GetMapping("/Project/OwnerName/{ownerName}")
    public Map<String, Object> getProjectsByOwnerName(@PathVariable("ownerName") String ownerName) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> userList = userService.getUserMapsByNameLike(ownerName);
        if (userList.isEmpty()) {
            map.put("code", 0);
            map.put("message", "项目查询成功");
            map.put("projects", new HashMap<>());
        }
        List<Integer> idList = new ArrayList<>();
        userList.forEach(item -> idList.add(Integer.parseInt(item.get("id").toString())));
        List<Project> projects = projectService.getProjectsByOwnerList(idList);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", convertUserID(projects));
        return map;
    }

    @PostMapping("/Project")
    public Map<String, Object> saveProject(@RequestBody Project project) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        project.setOwner((Integer) session.getAttribute("id"));
        logger.info(String.valueOf(project));
        if (projectService.save(project)) {
            map.put("code", 0);
            map.put("message", "项目创建成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }

    @PutMapping("/Project")
    public Map<String, Object> updateProject(@RequestBody Project project) {
        logger.info(String.valueOf(project));
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        Integer userID = Integer.parseInt(session.getAttribute("id").toString());
        if (!Objects.equals(project.getOwner(), userID)) {
            map.put("code", -2);
            map.put("message", "无法修改其他用户管理的项目");
            return map;
        }
        if (projectService.updateProjectsByIDAndOwner(project)) {
            map.put("code", 0);
            map.put("message", "项目信息修改成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }
}
