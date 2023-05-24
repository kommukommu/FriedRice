package com.friedrice.backendfriedrice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.friedrice.backendfriedrice.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    public User getUser(User user);

    public List<Map<String, Object>> getUserMapsByNameLike(String name);

    public List<Map<String, Object>> getUserMapsByID(List<Integer> ids);

    public Long countUsers(User user);
    public String getUserNameByID (Integer id);
}
