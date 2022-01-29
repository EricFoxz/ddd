package gitee.com.ericfox.ddd.apis.assembler;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDto;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Dto {
    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> DTO fromEntity(Class<DTO> dtoClass, ENTITY entity) {
        DTO dto = ReflectUtil.newInstance(dtoClass);
        dto.fromEntity(entity);
        return dto;
    }

    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> List<DTO> fromEntityList(Class<DTO> dtoClass, ENTITY... entities) {
        DTO dto = ReflectUtil.newInstance(dtoClass);
        return dto.fromEntities(entities);
    }

    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> List<DTO> fromEntityList(Class<DTO> dtoClass, List<ENTITY> entityList) {
        DTO dto = ReflectUtil.newInstance(dtoClass);
        return dto.fromEntityList(entityList);
    }

    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> PageInfo<DTO> fromEntityPage(Class<DTO> dtoClass, PageInfo<ENTITY> entityPageInfo) {
        DTO dto = ReflectUtil.newInstance(dtoClass);
        return dto.fromEntityPage(entityPageInfo);
    }
}
