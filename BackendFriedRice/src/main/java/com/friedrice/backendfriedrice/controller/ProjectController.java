package com.friedrice.backendfriedrice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProjectController {
    private final Logger logger;

    public ProjectController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/Project")
    public Map<String, Object> getProjects() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
