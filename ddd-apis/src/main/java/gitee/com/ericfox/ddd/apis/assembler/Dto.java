package gitee.com.ericfox.ddd.apis.assembler;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.interfaces.BaseDto;
import gitee.com.ericfox.ddd.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Dto {
    @SneakyThrows
    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>, T extends DTO> DTO fromEntity(Class<T> dtoClass, ENTITY entity) {
        DTO dto = dtoClass.newInstance();
        return dto.fromEntity(entity);
    }

    @SneakyThrows
    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> List<DTO> fromEntityList(Class<DTO> dtoClass, ENTITY... entities) {
        DTO dto = dtoClass.newInstance();
        return dto.fromEntities(entities);
    }

    @SneakyThrows
    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> List<DTO> fromEntityList(Class<DTO> dtoClass, List<ENTITY> entityList) {
        DTO dto = dtoClass.newInstance();
        return dto.fromEntityList(entityList);
    }

    @SneakyThrows
    public static <PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, DTO extends BaseDto<PO, ENTITY, DTO>> PageInfo<DTO> fromEntityPage(Class<DTO> dtoClass, PageInfo<ENTITY> entityPageInfo) {
        DTO dto = dtoClass.newInstance();
        return dto.fromEntityPage(entityPageInfo);
    }
}
