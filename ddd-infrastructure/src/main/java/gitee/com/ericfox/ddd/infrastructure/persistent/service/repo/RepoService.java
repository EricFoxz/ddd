package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy.OrmEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.constants.ActiveProperties;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RepoService implements RepoStrategy {
    private final String repoStrategy = ActiveProperties.customProperties.getRepoStrategy();
    private final Map<String, RepoStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public RepoService(Map<String, RepoStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> T findById(T t) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).findById(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean deleteById(T t) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).deleteById(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean multiDeleteById(List<T> t) {
        if (CollUtil.isNotEmpty(t)) {
            String beanName = getBeanName(t.get(0));
            return strategyMap.get(beanName).multiDeleteById(t);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean multiDeleteById(T... t) {
        if (ArrayUtil.isNotEmpty(t)) {
            String beanName = getBeanName(t[0]);
            return strategyMap.get(beanName).multiDeleteById(t);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> T insert(T t) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).insert(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(List<T> t) {
        if (CollUtil.isNotEmpty(t)) {
            String beanName = getBeanName(t.get(0));
            return strategyMap.get(beanName).multiInsert(t);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(T... t) {
        if (ArrayUtil.isNotEmpty(t)) {
            String beanName = getBeanName(t[0]);
            return strategyMap.get(beanName).multiInsert(t);
        }
        return true;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean updateById(T t) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).updateById(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).queryPage(t, pageNum, pageSize);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> List<T> queryList(T t, int limit) {
        String beanName = getBeanName(t);
        return strategyMap.get(beanName).queryList(t, limit);
    }

    private <T extends BasePo<T>> String getBeanName(T t) {
        OrmEnabledAnnotation declaredAnnotation = t.getClass().getDeclaredAnnotation(OrmEnabledAnnotation.class);
        if (declaredAnnotation == null) {
            return repoStrategy;
        } else {
            return declaredAnnotation.type().getCode();
        }
    }
}
