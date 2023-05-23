package com.friedrice.backendfriedrice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.friedrice.backendfriedrice.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
