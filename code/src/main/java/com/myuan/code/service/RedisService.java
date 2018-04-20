package com.myuan.code.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/*
 * @author liuwei
 * @date 2018/4/19 14:22
 *
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, String value, long time) {
        stringRedisTemplate.opsForValue().set(key, value);
        if(time != -1) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setObject(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value);
        if(time != -1) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(String key, List value, long time) {
        for (Object o : value) {
            redisTemplate.opsForList().rightPush(key, o);
        }
        if(time != -1) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }
    public List getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
