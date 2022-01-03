package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import com.jfinal.plugin.activerecord.Model;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

import java.io.Serializable;
import java.util.List;

public interface RepoStrategy {
    <T extends BasePo<T>> T findById(T t);

    <T extends BasePo<T>> boolean deleteById(T t);

    <T extends BasePo<T>> T insert(T t);

    <T extends BasePo<T>> boolean update(T t);

    <T extends BasePo<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize);

    default <T extends BasePo<T>> List<T> queryList(T t) {
        return queryList(t, 1000);
    }

    <T extends BasePo<T>> List<T> queryList(T t, int limit);
}
