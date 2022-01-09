package gitee.com.ericfox.ddd.infrastructure.general.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.JsonUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * redis配置
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {
    @Resource
    RedisConnectionFactory factory;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(600), // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder finalResult = new StringBuilder();
                // 必须有类名作为前缀，避免走入 Default 之后取的方法名一样造成无法类型转换
                finalResult.append(target.getClass().getSimpleName());
                finalResult.append(":");
                finalResult.append(method.getName());
                finalResult.append(":");

                if (params.length == 0) {
                    finalResult.append("noParams");
                    return finalResult.toString();
                }

                // 只含有一个参数位置，并且是基础类型，则进行特殊处理
                if (params.length == 1) {
                    Object param = params[0];
                    if (null == param) {
                        finalResult.append("nullParams");
                        return finalResult.toString();
                    }
                    Class<?> clazz = param.getClass();
                    if (checkClassBasicType(clazz)) {
                        finalResult.append(param);
                        return finalResult.toString();
                    }
                }

                // 非基础类型或多参数的场景
                StringBuilder paramString = new StringBuilder();
                for (int i = 0; i < params.length; i++) {
                    if (null == params[i]) {
                        paramString.append("nullParams");
                    } else {
                        paramString.append(JsonUtil.toJsonStr(params[i]));
                    }
                    if (i != params.length - 1) {
                        paramString.append(":");
                    }
                }

                String finalParam = paramString.toString();
                String sha256 = SecureUtil.sha256(finalParam);

                log.debug("keyGeneratorToServiceParam Method <{}>, Param <{}> SHA256 <{}>", method.getName(), finalParam, sha256);

                finalResult.append(sha256);
                return finalResult.toString();
            }
        };
    }

    @Bean
    public KeyGenerator keyGeneratorToServiceParam() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder finalResult = new StringBuilder();
                finalResult.append(target.getClass().getSimpleName());
                finalResult.append(":");
                finalResult.append(method.getName());
                finalResult.append(":");
                if (params.length == 0) {
                    finalResult.append("noParams");
                    return finalResult.toString();
                }
                // 只含有一个参数位置，并且是基础类型，则进行特殊处理
                if (params.length == 1) {
                    Object param = params[0];
                    if (null == param) {
                        finalResult.append("nullParams");
                        return finalResult.toString();
                    }
                    Class<?> clazz = param.getClass();
                    if (checkClassBasicType(clazz)) {
                        finalResult.append(param);
                        return finalResult.toString();
                    }
                }
                // 非基础类型或多参数的场景
                StringBuilder paramString = new StringBuilder();
                for (int i = 0; i < params.length; i++) {
                    if (null == params[i]) {
                        paramString.append("nullParams");
                    } else {
                        if (params[i] instanceof String
                                || params[i] instanceof Boolean
                                || params[i] instanceof Character
                                || params[i] instanceof Byte
                                || params[i] instanceof Short
                                || params[i] instanceof Integer
                                || params[i] instanceof Long
                                || params[i] instanceof Float
                                || params[i] instanceof Double
                                || params[i] instanceof BigDecimal
                        ) {
                            paramString.append(params[i]);
                        } else {
                            paramString.append(JsonUtil.toJsonStr(params[i]));
                        }
                    }
                    if (i != params.length - 1) {
                        paramString.append(":");
                    }
                }

                String finalParam = paramString.toString();
                String sha256 = SecureUtil.sha256(finalParam);

                log.debug("keyGeneratorToServiceParam <{}>, SHA <{}>", finalParam, sha256);

                finalResult.append(sha256);
                return finalResult.toString();
            }
        };
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("UserInfoList", this.getRedisCacheConfigurationWithTtl(3000));
        redisCacheConfigurationMap.put("UserInfoListAnother", this.getRedisCacheConfigurationWithTtl(18000));

        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }

    private Boolean checkClassBasicType(Class<?> clazz) {
        // 判断基本类型（boolean、char、byte、short、int、long、float、double）
        if (clazz.isPrimitive()) {
            return true;
        }
        // 判断原始类型
        String classTypeName = clazz.getName();
        ArrayList<String> basicTypeList = CollUtil.newArrayList(
                "java.lang.String",
                "java.lang.Boolean",
                "java.lang.Character",
                "java.lang.Byte",
                "java.lang.Short",
                "java.lang.Integer",
                "java.lang.Long",
                "java.lang.Float",
                "java.lang.Double"
        );
        return basicTypeList.contains(classTypeName);
    }
}
