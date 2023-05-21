package com.friedrice.backendfriedrice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.friedrice.backendfriedrice.mapper.UserMapper;
import com.friedrice.backendfriedrice.pojo.User;
import com.friedrice.backendfriedrice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendFriedRiceApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {

    }

    @Test
    void userMapperTest() {
        User user = new User();
        user.setName("user1");
        user.setPassword("pass");
        int result = userMapper.insert(user);
        System.out.println("===" + result);
    }

    @Test
    void userServiceTest() {
        User user = userService.getById(1);
        System.out.println("===" + user);
    }

    @Test
    void wrapperTest() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "user1");

        Long count = userService.count(queryWrapper);
        System.out.println(count);
    }
}
