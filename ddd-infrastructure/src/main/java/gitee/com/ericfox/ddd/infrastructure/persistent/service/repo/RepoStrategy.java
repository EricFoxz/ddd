package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 持久化策略接口
 */
public interface RepoStrategy {
    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V findById(V v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean deleteById(V v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(List<V> v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(V... v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V insert(V v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(List<V> v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(V... v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean updateById(V v);

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> PageInfo<V> queryPage(V v, int pageNum, int pageSize);

    @NonNull
    default <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> List<V> queryList(V v) {
        return queryList(v, 1000);
    }

    <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> List<V> queryList(V t, int limit);
}
