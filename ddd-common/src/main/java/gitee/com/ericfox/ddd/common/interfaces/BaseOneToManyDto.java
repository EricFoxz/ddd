package gitee.com.ericfox.ddd.common.interfaces;

import java.io.Serializable;

/**
 * 一对多
 */
public interface BaseOneToManyDto<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseOneToManyDto> extends Serializable {
}
