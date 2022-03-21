package com.dntech.localcache;

import com.dntech.localcache.dto.OrderDetails;
import com.dntech.localcache.service.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class LocalCacheApplication implements CommandLineRunner {

    @Autowired
    CacheManager cacheManager;

    private static final Logger LOG = LoggerFactory.getLogger(LocalCacheApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LocalCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        long dbStartTime = System.currentTimeMillis();
        OrderDetails dbResult = cacheManager.getOrderCache().getOrderDB("u0002", "22Dec2021", "i1237");
        LOG.info("DB Result: {}", dbResult);
        LOG.info("Time used to fetch data from database: " + (System.currentTimeMillis() - dbStartTime));

        long cacheStartTime = System.currentTimeMillis();
        Optional<OrderDetails> cacheResult = cacheManager.getOrderCache().get("u0002", "22Dec2021", "i1237");
        if (cacheResult.isPresent()){
            LOG.info("Cache Result: {}", cacheResult.get());
        } else {
            LOG.error("No result found for {} {} {}.", "u0002", "22Dec2021", "i1237");
        }
        LOG.info("Time used to fetch data from cache: "+ (System.currentTimeMillis()-cacheStartTime));
    }
}
