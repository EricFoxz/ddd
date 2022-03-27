package gitee.com.ericfox.ddd.common.interfaces.starter;

/**
 * 缓存策略接口
 */
public interface CacheService {
    void put(Object key, Object value);

    Object get(Object key);

    Boolean remove(Object key);

    Long removeByPrefix(String prefix);

    Long removeByPrefix(String module, String prefix);
}
