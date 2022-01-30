package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

import java.io.Serializable;

/**
 * 一对多
 */
public interface BaseOneToManyDto<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseOneToManyDto> extends Serializable {
}
