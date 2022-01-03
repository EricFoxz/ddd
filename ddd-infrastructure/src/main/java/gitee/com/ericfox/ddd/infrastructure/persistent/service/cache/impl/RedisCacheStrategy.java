package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.impl;

import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheStrategy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("redisCacheStrategy")
public class RedisCacheStrategy implements CacheStrategy {
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }
}
