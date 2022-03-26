package gitee.com.ericfox.ddd.common.interfaces.apis;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;

import java.io.Serializable;

/**
 * 一对多
 */
public interface BaseOneToManyDto<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseOneToManyDto> extends Serializable {
}
