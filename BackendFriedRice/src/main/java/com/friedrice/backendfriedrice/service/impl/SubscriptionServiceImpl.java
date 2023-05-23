package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.SubscriptionMapper;
import com.friedrice.backendfriedrice.pojo.Subscription;
import com.friedrice.backendfriedrice.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl extends ServiceImpl<SubscriptionMapper, Subscription> implements SubscriptionService {
    @Override
    public List<Subscription> getSubscriptions(Subscription subscription) {
        LambdaQueryWrapper<Subscription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(subscription.getSubscriber() != null, Subscription::getSubscriber, subscription.getSubscriber())
                .eq(subscription.getSubscribed() != null, Subscription::getSubscribed, subscription.getSubscribed());
        return this.list(queryWrapper);
    }

    @Override
    public Long countSubscriptions(Subscription subscription) {
        LambdaQueryWrapper<Subscription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(subscription.getSubscriber() != null, Subscription::getSubscriber, subscription.getSubscriber())
                .eq(subscription.getSubscribed() != null, Subscription::getSubscribed, subscription.getSubscribed());
        return this.count(queryWrapper);
    }

    @Override
    public Boolean removeSubscriptions(Integer subscriber, List<Integer> subscribedIDs) {
        LambdaQueryWrapper<Subscription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subscription::getSubscriber, subscriber)
                .in(Subscription::getSubscribed, subscribedIDs);
        return this.remove(queryWrapper);
    }
}
