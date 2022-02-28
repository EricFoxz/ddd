package gitee.com.ericfox.ddd.common.interfaces;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> {
    <E extends ENTITY> E findById(Long id);

    <E extends ENTITY> E findFirst(ENTITY entity);

    <E extends ENTITY> PageInfo<E> queryPage(ENTITY entity, int pageNum, int pageSize);

    List<ENTITY> queryList(ENTITY entity, int pageSize);

    ENTITY insert(ENTITY entity);

    boolean update(ENTITY entity);

    boolean deleteById(ENTITY entity);

    boolean multiDeleteById(List<ENTITY> entityList);

    void cacheEvict();
}
