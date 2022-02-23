package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache;

import gitee.com.ericfox.ddd.common.enums.strategy.CacheTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存服务service
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "custom.service.cache-strategy", value = "enable")
public class CacheService implements CacheStrategy {
    @Resource
    private ServiceProperties serviceProperties;

    private final List<CacheTypeStrategyEnum> strategyEnumList = CollUtil.newArrayList();

    private final Map<String, CacheStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private CacheService(Map<String, CacheStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    public void put(Object key, Object value) {
        strategyMap.get(getBeanName()).put(key, value);
    }

    public Object get(Object key) {
        return strategyMap.get(getBeanName()).get(key);
    }

    @Override
    public Boolean delete(Object key) {
        return strategyMap.get(getBeanName()).delete(key);
    }

    @Override
    public Long flushByPrefix(String prefix) {
        return strategyMap.get(getBeanName()).flushByPrefix(prefix);
    }

    private String getBeanName() {
        if (CollUtil.isEmpty(this.strategyEnumList)) {
            for (ServiceProperties.CacheStrategyBean.CachePropertiesEnum defaultStrategy : serviceProperties.getCacheStrategy().getDefaultStrategy()) {
                this.strategyEnumList.add(defaultStrategy.toBizEnum());
            }
        }
        return this.strategyEnumList.get(0).getCode();
    }
}
