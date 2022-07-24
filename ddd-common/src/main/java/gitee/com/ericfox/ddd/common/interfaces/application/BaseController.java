package gitee.com.ericfox.ddd.common.interfaces.application;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.interfaces.apis.BaseDetailParam;
import gitee.com.ericfox.ddd.common.interfaces.apis.BaseDto;
import gitee.com.ericfox.ddd.common.interfaces.apis.BaseHttpStatus;
import gitee.com.ericfox.ddd.common.interfaces.apis.BasePageParam;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BaseController<
        PO extends BasePo<PO>,
        ENTITY extends BaseEntity<PO, ENTITY>,
        PARAM extends BasePageParam<PO, ENTITY>,
        DETAIL extends BaseDetailParam<PO, ENTITY>,
        DTO extends BaseDto<PO, ENTITY, DTO>
        > extends BaseHttpStatus {

    Mono<DTO> detail(Long id);

    Mono<PageInfo<DTO>> page(PARAM param);

    Mono<List<DTO>> list(PARAM param);

    Mono<DTO> create(DETAIL detail);

    Mono<DTO> edit(DETAIL detail);

    Mono<DTO> remove(DETAIL detail);

    Mono<List<DTO>> multiRemove(List<DETAIL> list);
}
