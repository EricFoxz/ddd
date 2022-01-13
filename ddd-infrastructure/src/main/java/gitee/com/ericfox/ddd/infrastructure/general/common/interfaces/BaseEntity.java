package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans.SimpleCondition;

@SuppressWarnings("unchecked")
public interface BaseEntity<T extends BasePo<T>, V extends BaseEntity<T, V>> {
    T toPo();

    V fromPo(T t);

    default boolean create() {
        return getService().insert((V) this) != null;
    }

    default boolean delete() {
        return getService().deleteById((V) this);
    }

    default boolean update() {
        return getService().update((V) this);
    }

    <U extends BaseService<T, V>> U getService();

    BaseCondition<?> get_condition();

    void set_condition(BaseCondition<?> condition);

    default BaseCondition<?> toCondition() {
        return SimpleCondition.newInstance(this.toPo());
    }
}
