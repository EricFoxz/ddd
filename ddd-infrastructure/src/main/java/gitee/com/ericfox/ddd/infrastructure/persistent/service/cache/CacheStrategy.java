package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache;

public interface CacheStrategy {
    void set(String key, Object value);
    Object get(String key);
}
