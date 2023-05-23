package com.friedrice.backendfriedrice.controller;

import com.friedrice.backendfriedrice.pojo.Project;
import com.friedrice.backendfriedrice.service.ProjectService;
import com.friedrice.backendfriedrice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {
    private final Logger logger;
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/Project")
    public Map<String, Object> getProjectsByName() {
        Map<String, Object> map = new HashMap<>();
        List<Project> projects = projectService.getProjectsByNameLike(null);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", projects);
        return map;
    }

    @GetMapping("/Project/Name/{name}")
    public Map<String, Object> getProjectsByName(@PathVariable("name") String name) {
        Map<String, Object> map = new HashMap<>();
        List<Project> projects = projectService.getProjectsByNameLike(name);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", projects);
        return map;
    }

    @GetMapping("/Project/Owner/{owner}")
    public Map<String, Object> getProjectsByOwner(@PathVariable("owner") Integer owner) {
        Map<String, Object> map = new HashMap<>();
        List<Project>  projects = projectService.getProjectsByOwnerList(owner);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", projects);
        return map;
    }

    @GetMapping("/Project/OwnerName/{ownerName}")
    public Map<String, Object> getProjectsByOwnerName(@PathVariable("ownerName") String ownerName) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> userList = userService.getUserMapsByNameLike(ownerName);
        List<Integer> idList = new ArrayList<>();
        userList.forEach(item -> idList.add((Integer) item.get("id")));
        List<Project> projects = projectService.getProjectsByOwnerList(idList);
        map.put("code", 0);
        map.put("message", "项目查询成功");
        map.put("projects", projects);
        return map;
    }
}
