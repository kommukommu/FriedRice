package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.SubscriptionMapper;
import com.friedrice.backendfriedrice.pojo.Subscription;
import com.friedrice.backendfriedrice.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl extends ServiceImpl<SubscriptionMapper, Subscription> implements SubscriptionService {
}
