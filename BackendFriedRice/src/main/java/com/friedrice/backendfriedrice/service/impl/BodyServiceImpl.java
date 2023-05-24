package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.BodyMapper;
import com.friedrice.backendfriedrice.pojo.Body;
import com.friedrice.backendfriedrice.service.BodyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BodyServiceImpl extends ServiceImpl<BodyMapper, Body> implements BodyService {
    @Override
    public Boolean createNewBodiesByArticle(Integer articleID) {
        Body body1 = new Body();
        body1.setParent(articleID);
        body1.setBody("");
        body1.setIsLatest((short) 0);
        Body body2 = new Body();
        body2.setParent(articleID);
        body2.setBody("");
        body2.setIsLatest((short) 1);
        List<Body> bodies = new ArrayList<>();
        bodies.add(body1);
        bodies.add(body2);
        return this.saveBatch(bodies);
    }
}
