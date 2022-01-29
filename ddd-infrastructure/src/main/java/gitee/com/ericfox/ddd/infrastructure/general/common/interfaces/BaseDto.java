package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import java.io.Serializable;

public interface BaseDto <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> extends Serializable {
}
