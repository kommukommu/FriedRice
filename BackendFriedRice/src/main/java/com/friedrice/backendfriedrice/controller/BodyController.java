package com.friedrice.backendfriedrice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BodyController {
    private final HttpServletRequest request;
    private final Logger logger;

    public BodyController(HttpServletRequest request) {
        this.request = request;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}
