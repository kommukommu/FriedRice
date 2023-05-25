package com.friedrice.backendfriedrice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.friedrice.backendfriedrice.pojo.Body;

public interface BodyService extends IService<Body> {
    public Boolean createNewBodiesByArticle (Integer articleID);
    public Body getBody(Integer articleID);
    public Body getBody(Integer articleID, Integer articleState);
    public Boolean updateBody(Integer articleID, Body body);
}
