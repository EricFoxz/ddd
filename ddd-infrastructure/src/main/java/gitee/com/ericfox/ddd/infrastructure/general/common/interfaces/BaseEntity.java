package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans.SimpleCondition;

@SuppressWarnings("unchecked")
public interface BaseEntity<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> {
    PO toPo();

    ENTITY fromPo(PO po);

    default boolean create() {
        return getService().insert((ENTITY) this) != null;
    }

    default boolean delete() {
        return getService().deleteById((ENTITY) this);
    }

    default boolean update() {
        return getService().update((ENTITY) this);
    }

    <U extends BaseService<PO, ENTITY>> U getService();

    BaseCondition<?> get_condition();

    void set_condition(BaseCondition<?> condition);

    default BaseCondition<?> toCondition() {
        return SimpleCondition.newInstance(this.toPo());
    }
}
