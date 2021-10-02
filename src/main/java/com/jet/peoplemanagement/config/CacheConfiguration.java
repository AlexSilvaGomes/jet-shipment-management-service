package com.jet.peoplemanagement.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(Ticker ticker) {
        CaffeineCache messageCache = buildCache("params", ticker, 5);
        CaffeineCache mapRegions = buildCache("mapRegions", ticker, 5);
        CaffeineCache meliOrdersCache = buildCache("getOrdersBySeller", ticker, 10);
        CaffeineCache meliShipmentById = buildCache("getShipmentById", ticker, 10);
        CaffeineCache meliUsersMe = buildCache("usersMe", ticker, 1500);

        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(messageCache, mapRegions, meliOrdersCache, meliShipmentById, meliUsersMe));
        return manager;
    }

    private CaffeineCache buildCache(String name, Ticker ticker, int minutesToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
                .maximumSize(100)
                .ticker(ticker)
                .build());
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }
}
