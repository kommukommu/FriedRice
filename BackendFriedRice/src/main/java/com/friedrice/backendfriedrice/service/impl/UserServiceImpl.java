package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.SubscriptionMapper;
import com.friedrice.backendfriedrice.mapper.UserMapper;
import com.friedrice.backendfriedrice.pojo.User;
import com.friedrice.backendfriedrice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
