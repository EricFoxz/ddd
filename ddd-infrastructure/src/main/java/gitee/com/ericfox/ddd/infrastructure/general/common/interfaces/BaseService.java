package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> {
    ENTITY findById(Long id);

    PageInfo<ENTITY> queryPage(ENTITY entity, int pageNum, int pageSize);

    List<ENTITY> queryList(ENTITY entity, int pageSize);

    ENTITY insert(ENTITY entity);

    boolean update(ENTITY entity);

    boolean deleteById(ENTITY entity);
}
