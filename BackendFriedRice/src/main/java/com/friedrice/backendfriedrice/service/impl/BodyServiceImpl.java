package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.BodyMapper;
import com.friedrice.backendfriedrice.pojo.Body;
import com.friedrice.backendfriedrice.service.BodyService;
import org.springframework.stereotype.Service;

@Service
public class BodyServiceImpl extends ServiceImpl<BodyMapper, Body> implements BodyService {
}
