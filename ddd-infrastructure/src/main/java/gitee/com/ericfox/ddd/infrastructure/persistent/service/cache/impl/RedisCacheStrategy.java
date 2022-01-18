package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.impl;

import cn.hutool.core.collection.CollectionUtil;
import gitee.com.ericfox.ddd.infrastructure.general.config.service.RedisCacheConfig;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("redisCacheStrategy")
@Slf4j
@ConditionalOnBean(value = RedisCacheConfig.class)
public class RedisCacheStrategy implements CacheStrategy {
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public void put(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean delete(Object key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long flushByPrefix(String prefix) {
        String UUID = cn.hutool.core.lang.UUID.fastUUID().toString();
        // 扫描指定前缀的key并删除
        String luaFun = "\n" +
                "redis.replicate_commands()\n" +
                "local cursor = 0\n" +
                "local keyNum = 0  \n" +
                "repeat\n" +
                "   local res = redis.call('scan',cursor,'match',KEYS[1],'count',ARGV[1])\n" +
                "   if(res ~= nil and #res>=0) \n" +
                "   then\n" +
                "      cursor = tonumber(res[1])\n" +
                "      local ks = res[2]\n" +
                "      if(ks ~= nil and #ks>0) \n" +
                "      then\n" +
                "         for i=1,#ks,1 do\n" +
                "            local key = tostring(ks[i])\n" +
                "            redis.call('del',key)\n" +
                "         end\n" +
                "         keyNum = keyNum + #ks\n" +
                "      end\n" +
                "     end\n" +
                "until( cursor <= 0 )\n" +
                "return keyNum";
        // luaFun = "scan match '" + prefix + "' count 2000";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaFun, Long.class);
        try {
            return (Long) redisTemplate.execute(redisScript, CollectionUtil.newArrayList(prefix), 100);
        } catch (Exception e) {
            log.error("redisCache清除指定前缀缓存出错", e);
        }
        return 0L;
    }
}
