package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.apis.model.param.BaseDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.BasePageParam;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import org.springframework.http.ResponseEntity;

public interface BaseController<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, PARAM extends BasePageParam<PO, ENTITY>, N extends BaseDetailParam<PO, ENTITY>> {
    ResponseEntity<?> detail(Long id);

    ResponseEntity<?> page(PARAM param);

    ResponseEntity<?> list(PARAM param);

    ResponseEntity<?> create(ENTITY v);

    ResponseEntity<?> edit(ENTITY ENTITY);

    ResponseEntity<?> remove(ENTITY ENTITY);
}
