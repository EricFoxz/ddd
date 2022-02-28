package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.apis.model.param.BaseDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.BasePageParam;
import gitee.com.ericfox.ddd.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, PARAM extends BasePageParam<PO, ENTITY>, N extends BaseDetailParam<PO, ENTITY>> {
    default ResponseEntity<?> detail(Long id) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> page(PARAM param) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> list(PARAM param) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> create(ENTITY v) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> edit(ENTITY ENTITY) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> remove(ENTITY ENTITY) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> multiRemove(List<ENTITY> list) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
