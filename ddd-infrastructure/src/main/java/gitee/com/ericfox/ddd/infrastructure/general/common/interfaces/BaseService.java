package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<ENTITY extends BaseEntity> {
    <E extends ENTITY> E findById(Long id);

    <E extends ENTITY> PageInfo<E> queryPage(ENTITY entity, int pageNum, int pageSize);

    List<ENTITY> queryList(ENTITY entity, int pageSize);

    ENTITY insert(ENTITY entity);

    boolean update(ENTITY entity);

    boolean deleteById(ENTITY entity);
}
