package com.friedrice.backendfriedrice.controller;

import com.friedrice.backendfriedrice.pojo.Subscription;
import com.friedrice.backendfriedrice.pojo.User;
import com.friedrice.backendfriedrice.service.SubscriptionService;
import com.friedrice.backendfriedrice.service.UserService;
import com.friedrice.backendfriedrice.utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final HttpServletRequest request;
    private final Utils utils;
    private final Logger logger;

    public UserController(UserService userService, SubscriptionService subscriptionService, HttpServletRequest request, Utils utils) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.request = request;
        this.utils = utils;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/User/{id}")
    public Map<String, Object> getUser(@PathVariable("id") Integer id) {
        User user = new User();
        user.setId(id);
        Map<String, Object> map = new HashMap<>();
        user = userService.getUser(user);
        if (user == null) {
            map.put("code", -1);
            map.put("message", "用户不存在");
            return map;
        }
        map.put("code", 0);
        map.put("message", "用户查询成功");
        map.put("id", user.getId());
        map.put("name", user.getName());
        return map;
    }

    @PostMapping("/User")
    public Map<String, Object> saveUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        User userName = new User();
        userName.setName(user.getName());
        if (Boolean.FALSE.equals(utils.validatePassword(user.getPassword()))) {
            map.put("code", -2);
            map.put("message", "密码只能包含英文字母、数字、“_”");
        } else if (userService.countUsers(userName) != 0) {
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

    @GetMapping("/Login")
    public Map<String, Object> isOnline() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        map.put("code", 0);
        map.put("id", session.getAttribute("id"));
        map.put("name", session.getAttribute("name"));
        map.put("message", "已登录");
        return map;
    }

    @PostMapping("/Login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        if (userService.countUsers(user) != 0) {
            User userGet = userService.getUser(user);
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
        HttpSession session = request.getSession(false);
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

    public List<Map<String, Object>> getUserList(HttpSession session, boolean isSelf) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(Integer.parseInt(session.getAttribute("id").toString()));
        List<Subscription> subscriptionList = subscriptionService.getSubscriptions(subscription);
        logger.info("subscriptionList->%s".formatted(subscriptionList.toString()));
        if (subscriptionList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> idList = new ArrayList<>();
        if (isSelf) idList.add(subscription.getSubscriber());
        subscriptionList.forEach(item -> idList.add(item.getSubscribed()));
        logger.info("subscriptionList->%s".formatted(idList));
        List<Map<String, Object>> userList = userService.getUserMapsByID(idList);
        logger.info("subscriptionList->%s".formatted(userList.toString()));
        return userList;
    }

    @GetMapping("/WriterList")
    public Map<String, Object> getWriterList() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        map.put("code", 0);
        map.put("message", "关注列表查询成功");
        map.put("list", getUserList(session, true));
        return map;
    }

    @GetMapping("/Subscription")
    public Map<String, Object> getSubscription() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        map.put("code", 0);
        map.put("message", "关注列表查询成功");
        map.put("list", getUserList(session, false));
        return map;
    }

    @PostMapping("/Subscription")
    public Map<String, Object> subscribe(@RequestBody Subscription subscription) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        Integer subscriber = Integer.parseInt(session.getAttribute("id").toString());
        Integer subscribed = subscription.getSubscribed();
        if (subscriber.equals(subscribed)) {
            map.put("code", -2);
            map.put("message", "不能关注自己");
            return map;
        }
        User userID = new User();
        userID.setId(subscribed);
        if (userService.countUsers(userID) < 1) {
            map.put("code", -3);
            map.put("message", "被关注者不存在");
            return map;
        }
        subscription.setSubscriber(subscriber);
        if (subscriptionService.countSubscriptions(subscription) != 0) {
            map.put("code", -4);
            map.put("message", "已关注此用户");
            return map;
        }
        if (subscriptionService.save(subscription)) {
            map.put("code", 0);
            map.put("message", "关注成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
        return map;
    }

    @DeleteMapping("/Subscription")
    public Map<String, Object> removeSubscriptions(@RequestBody List<Integer> idList) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            map.put("code", -1);
            map.put("message", "请先登录");
            return map;
        }
        if (idList.isEmpty()) {
            map.put("code", 0);
            map.put("message", "取消关注成功");
            return map;
        }
        if (subscriptionService.removeSubscriptions((Integer) session.getAttribute("id"),
                idList)) {
            map.put("code", 0);
            map.put("message", "取消关注成功");
            return map;
        }
        map.put("code", -100);
        map.put("message", "未知原因失败");
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
        map.put("id", session.getAttribute("id"));
        map.put("name", session.getAttribute("name"));
        map.put("session", session.toString());
        return map;
    }
}
