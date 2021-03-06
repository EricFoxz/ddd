package gitee.com.ericfox.ddd.infrastructure.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BaseDao;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.IdUtil;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.InfrastructureProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久化service
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class RepoService implements RepoStrategy {
    @Resource
    private InfrastructureProperties infrastructureProperties;

    private RepoTypeStrategyEnum repoStrategy;
    private final Map<String, RepoStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public RepoService(Map<String, RepoStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> ENTITY findById(ENTITY entity) {
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).findById(entity);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> ENTITY findFirst(ENTITY entity) {
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).findById(entity);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean deleteById(ENTITY entity) {
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).deleteById(entity);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiDeleteById(List<ENTITY> entityList) {
        if (CollUtil.isNotEmpty(entityList)) {
            String beanName = getBeanName(entityList.get(0));
            return strategyMap.get(beanName).multiDeleteById(entityList);
        }
        return true;
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiDeleteById(ENTITY... entities) {
        if (ArrayUtil.isNotEmpty(entities)) {
            String beanName = getBeanName(entities[0]);
            return strategyMap.get(beanName).multiDeleteById(entities);
        }
        return true;
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> ENTITY insert(ENTITY entity) {
        if (entity.getId() == null) {
            entity.setId(IdUtil.getSnowflakeNextId());
        }
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).insert(entity);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiInsert(List<ENTITY> entityList) {
        if (CollUtil.isNotEmpty(entityList)) {
            for (ENTITY entity : entityList) {
                if (entity.getId() == null) {
                    entity.setId(IdUtil.getSnowflakeNextId());
                }
            }
            String beanName = getBeanName(entityList.get(0));
            return strategyMap.get(beanName).multiInsert(entityList);
        }
        return true;
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiInsert(ENTITY... entities) {
        if (ArrayUtil.isNotEmpty(entities)) {
            String beanName = getBeanName(entities[0]);
            return strategyMap.get(beanName).multiInsert(entities);
        }
        return true;
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean updateById(ENTITY entity) {
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).updateById(entity);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> PageInfo<ENTITY> queryPage(ENTITY entity, int pageNum, int pageSize) {
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).queryPage(entity, pageNum, pageSize);
    }

    @Override
    public <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> List<ENTITY> queryList(ENTITY entity, int limit) {
        String beanName = getBeanName(entity);
        return strategyMap.get(beanName).queryList(entity, limit);
    }

    private <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> String getBeanName(ENTITY entity) {
        RepoEnabledAnnotation declaredAnnotation = entity.toPo().getClass().getDeclaredAnnotation(RepoEnabledAnnotation.class);
        if (declaredAnnotation == null) {
            if (repoStrategy == null) {
                repoStrategy = infrastructureProperties.getRepoStrategy().getDefaultStrategy().toBizEnum();
            }
            return repoStrategy.getCode();
        } else {
            return declaredAnnotation.type().getCode();
        }
    }
}
