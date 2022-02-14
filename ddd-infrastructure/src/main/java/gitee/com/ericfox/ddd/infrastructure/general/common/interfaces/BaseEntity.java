package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans.SimpleCondition;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public interface BaseEntity<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> extends BaseContext.BasePartPlaceThing, Runnable, Serializable {
    void setId(Long id);

    Long getId();

    PO toPo();

    ENTITY fromPo(PO po);

    <SERVICE extends BaseService<PO, ENTITY>> SERVICE service();

    default boolean create() {
        return service().insert((ENTITY) this) != null;
    }

    default boolean delete() {
        return service().deleteById((ENTITY) this);
    }

    default boolean update() {
        return service().update((ENTITY) this);
    }

    BaseCondition<?> get_condition();

    void set_condition(BaseCondition<?> condition);

    default BaseCondition<?> toCondition() {
        return SimpleCondition.newInstance(this.toPo());
    }
}
