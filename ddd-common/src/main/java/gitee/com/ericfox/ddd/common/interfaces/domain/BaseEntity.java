package gitee.com.ericfox.ddd.common.interfaces.domain;

import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.trans.SimpleCondition;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public interface BaseEntity<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> extends
        BaseContext.BasePartPlaceThing, Serializable {
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

    <DESCRIPTION extends BaseContext.BaseDescription> void set_description(DESCRIPTION _description);

    <MOMENT extends BaseContext.BaseMoment> void set_moment(MOMENT _moment);

    <RULE extends BaseContext.BaseRule> void set_rule(RULE _rule);

    default BaseCondition<?> toCondition() {
        return SimpleCondition.newInstance(this.toPo());
    }
}
