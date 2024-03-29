package com.dayuanit.movie.movie1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean cacheSeatInfo(int scheduleId,int row,int col) {
        String key = "movie:ticket:" + scheduleId + ":" + row + ":" + col;

        return redisTemplate.opsForValue().setIfAbsent(key, " ", 60, TimeUnit.SECONDS);
    }
}
