package com.friedrice.backendfriedrice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.friedrice.backendfriedrice.pojo.Subscription;

import java.util.List;

public interface SubscriptionService extends IService<Subscription> {
    public List<Subscription> getSubscriptions (Subscription subscription);
    public Long countSubscriptions (Subscription subscription);
    public Boolean removeSubscriptions (Integer subscriber, List<Integer> subscribedIDs);
}
