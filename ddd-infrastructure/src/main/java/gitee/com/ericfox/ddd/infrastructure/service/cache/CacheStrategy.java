package gitee.com.ericfox.ddd.infrastructure.service.cache;

/**
 * 缓存策略接口
 */
public interface CacheStrategy {
    void put(Object key, Object value);

    Object get(Object key);

    Boolean delete(Object key);

    Long flushByPrefix(String prefix);
}
