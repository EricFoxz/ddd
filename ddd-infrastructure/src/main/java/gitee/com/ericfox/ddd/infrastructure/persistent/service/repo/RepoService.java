package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.OrmEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RepoService implements RepoStrategy {
    @Resource
    private ServiceProperties serviceProperties;

    private RepoTypeStrategyEnum repoStrategy;
    private final Map<String, RepoStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public RepoService(Map<String, RepoStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V findById(V t) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).findById(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean deleteById(V v) {
        String beanName = getBeanName(v);
        return strategyMap.get(beanName).deleteById(v);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(List<V> v) {
        if (CollUtil.isNotEmpty(v)) {
            String beanName = getBeanName(v.get(0));
            return strategyMap.get(beanName).multiDeleteById(v);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(V... t) {
        if (ArrayUtil.isNotEmpty(t)) {
            String beanName = getBeanName(t[0]);
            return strategyMap.get(beanName).multiDeleteById(t);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V insert(V t) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).insert(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(List<V> t) {
        if (CollUtil.isNotEmpty(t)) {
            String beanName = getBeanName(t.get(0));
            return strategyMap.get(beanName).multiInsert(t);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(V... v) {
        if (ArrayUtil.isNotEmpty(v)) {
            String beanName = getBeanName(v[0]);
            return strategyMap.get(beanName).multiInsert(v);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean updateById(V v) {
        String beanName = getBeanName(v);
        return strategyMap.get(beanName).updateById(v);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> PageInfo<V> queryPage(V v, int pageNum, int pageSize) {
        String beanName = getBeanName(v);
        return strategyMap.get(beanName).queryPage(v, pageNum, pageSize);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> List<V> queryList(V v, int limit) {
        String beanName = getBeanName(v);
        return strategyMap.get(beanName).queryList(v, limit);
    }

    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> String getBeanName(V v) {
        OrmEnabledAnnotation declaredAnnotation = v.toPo().getClass().getDeclaredAnnotation(OrmEnabledAnnotation.class);
        if (declaredAnnotation == null) {
            if (repoStrategy == null) {
                repoStrategy = serviceProperties.getRepoStrategy().getDefaultStrategy().toBizEnum();
            }
            return repoStrategy.getCode();
        } else {
            return declaredAnnotation.type().getCode();
        }
    }
}
