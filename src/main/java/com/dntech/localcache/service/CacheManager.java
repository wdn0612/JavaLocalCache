package com.dntech.localcache.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class CacheManager {

    private static final Logger LOG = LoggerFactory.getLogger(CacheManager.class);

    @Autowired
    private OrderCache orderCache;

    public CacheManager(OrderCache orderCache) {
        this.orderCache = orderCache;
    }

    @PostConstruct
    public void load(){
        LOG.info("CacheManager loading from database...");
        List<OrderCache> cacheList = Arrays.asList(orderCache);
        cacheList.forEach(Cache::load);
    }

}
