package gitee.com.ericfox.ddd.common.interfaces.apis;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;

import java.io.Serializable;
import java.util.List;

/**
 * 单表Dto
 *
 * @param <PO>
 * @param <ENTITY>
 * @param <DTO>
 */
public interface BaseDto<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> extends Serializable {
    DTO fromEntity(ENTITY entity);

    List<DTO> fromEntityList(List<ENTITY> entityList);

    List<DTO> fromEntities(ENTITY... entities);

    PageInfo<DTO> fromEntityPage(PageInfo<ENTITY> entities);
}
