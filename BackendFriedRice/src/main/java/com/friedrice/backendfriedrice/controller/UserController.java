package com.friedrice.backendfriedrice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.friedrice.backendfriedrice.pojo.User;
import com.friedrice.backendfriedrice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class UserController {
    private final UserService userService;
    private final HttpServletRequest request;

    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    private Boolean validatePassword(String str) {

        Pattern pattern = Pattern.compile("^[A-Z|a-z|\\d|_]*$");
        Boolean result = pattern.matcher(str).matches();
        System.out.println(str + result);
        return result;
    }

    @GetMapping("/User/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return user;
    }

    @PostMapping("/User")
    public Map<String, Object> saveUser(@RequestBody User user) {
        System.out.println("===" + user);
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", user.getName());

        if (!validatePassword(user.getPassword())) {
            map.put("code", -2);
            map.put("message", "密码只能包含英文字母、数字、“_”");
        } else if (userService.count(queryWrapper) != 0) {
            map.put("code", -1);
            map.put("message", "用户名已存在");
        } else if (userService.save(user)) {
            HttpSession session = request.getSession();
            session.setAttribute("name", user.getName());
            session.setAttribute("id", user.getId());

            map.put("code", 0);
            map.put("message", "注册成功");
            map.put("id", user.getId());
            map.put("name", user.getName());
        } else {
            map.put("code", -100);
            map.put("message", "未知原因失败");
        }

        return map;
    }

    @PostMapping("/Login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", user.getName())
                .eq("password", user.getPassword());
        if (userService.count(queryWrapper) != 0) {
            User userGet = userService.getOne(queryWrapper);
            HttpSession session = request.getSession();
            session.setAttribute("name", userGet.getName());
            session.setAttribute("id", userGet.getId());

            map.put("code", 0);
            map.put("id", userGet.getId());
            map.put("name", userGet.getName());
            map.put("message", "登录成功");
        } else {
            map.put("code", -1);
            map.put("message", "用户名或密码有误");
        }

        return map;
    }

    @GetMapping("/Logout")
    public Map<String, Object> logout() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session;
        session = request.getSession(false);
        if (session == null) {
            map.put("code", 1);
            map.put("message", "用户已登出");
        } else {
            map.put("code", 0);
            map.put("id", session.getAttribute("id"));
            map.put("name", session.getAttribute("name"));

            session.invalidate();
            map.put("message", "登出成功");
        }
        return map;
    }

    @GetMapping("/Get")
    public Map<String, Object> get() {
        HttpSession session;
        session = request.getSession(false);
        Map<String, Object> map = new HashMap<>();
        if (session == null) {
            map.put("session", "null");
            return map;
        }
        System.out.println(session);
        map.put("id", session.getAttribute("id"));
        map.put("name", session.getAttribute("name"));
        map.put("session", session.toString());
        return map;
    }
}
