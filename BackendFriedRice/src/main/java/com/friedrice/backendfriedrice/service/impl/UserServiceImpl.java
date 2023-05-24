package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.UserMapper;
import com.friedrice.backendfriedrice.pojo.User;
import com.friedrice.backendfriedrice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUser(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getId() != null, User::getId, user.getId())
                .eq(user.getName() != null, User::getName, user.getName())
                .eq(user.getPassword() != null, User::getPassword, user.getPassword());
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> getUserMapsByNameLike(String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), User::getName, name)
                .select(User::getId, User::getName);
        return this.listMaps(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> getUserMapsByID(List<Integer> ids) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getId, ids)
                .select(User::getId, User::getName);
        return this.listMaps(queryWrapper);
    }

    @Override
    public Long countUsers(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getId() != null, User::getId, user.getId())
                .eq(user.getName() != null, User::getName, user.getName())
                .eq(user.getPassword() != null, User::getPassword, user.getPassword());
        return this.count(queryWrapper);
    }

    @Override
    public String getUserNameByID(Integer id) {
        User user = getById(id);
        if (user == null){
            return null;
        }
        return user.getName();
    }
}
