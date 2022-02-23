package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 持久化策略接口
 */
public interface RepoStrategy {
    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> ENTITY findById(ENTITY entity);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean deleteById(ENTITY entity);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiDeleteById(List<ENTITY> entity);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiDeleteById(ENTITY... entities);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> ENTITY insert(ENTITY entity);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiInsert(List<ENTITY> entityList);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean multiInsert(ENTITY... entities);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> boolean updateById(ENTITY entity);

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> PageInfo<ENTITY> queryPage(ENTITY entity, int pageNum, int pageSize);

    @NonNull
    default <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> List<ENTITY> queryList(ENTITY entity) {
        return queryList(entity, 1000);
    }

    <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> List<ENTITY> queryList(ENTITY entity, int limit);
}
