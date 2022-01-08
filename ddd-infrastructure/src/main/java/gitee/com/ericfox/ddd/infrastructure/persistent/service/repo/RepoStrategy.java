package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import org.springframework.lang.NonNull;

import java.util.List;

public interface RepoStrategy {
    <T extends BasePo<T>, U extends BaseDao<T>> T findById(T t);

    <T extends BasePo<T>, U extends BaseDao<T>> boolean deleteById(T t);

    <T extends BasePo<T>, U extends BaseDao<T>> boolean multiDeleteById(List<T> t);

    <T extends BasePo<T>, U extends BaseDao<T>> boolean multiDeleteById(T... t);

    <T extends BasePo<T>, U extends BaseDao<T>> T insert(T t);

    <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(List<T> t);

    <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(T... t);

    <T extends BasePo<T>, U extends BaseDao<T>> boolean updateById(T t);

    <T extends BasePo<T>, U extends BaseDao<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize);

    @NonNull
    default <T extends BasePo<T>, U extends BaseDao<T>> List<T> queryList(T t) {
        return queryList(t, 1000);
    }

    <T extends BasePo<T>, U extends BaseDao<T>> List<T> queryList(T t, int limit);
}
