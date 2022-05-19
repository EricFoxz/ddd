package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.apis.model.param.BaseDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.BasePageParam;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>, PARAM extends BasePageParam<PO, ENTITY>, N extends BaseDetailParam<PO, ENTITY>> {
    static HttpStatus CONTINUE_100 = HttpStatus.CONTINUE;
    static HttpStatus OK_200 = HttpStatus.OK;
    static HttpStatus CREATED_201 = HttpStatus.CREATED;
    static HttpStatus NO_CONTENT_204 = HttpStatus.NO_CONTENT;
    static HttpStatus RESET_CONTENT_205 = HttpStatus.RESET_CONTENT;
    static HttpStatus MOVED_PERMANENTLY_301 = HttpStatus.MOVED_PERMANENTLY;
    static HttpStatus FOUND_302 = HttpStatus.FOUND;
    static HttpStatus NOT_MODIFIED_304 = HttpStatus.NOT_MODIFIED;
    static HttpStatus FORBIDDEN_403 = HttpStatus.FORBIDDEN;
    static HttpStatus NOT_FOUND_404 = HttpStatus.NOT_FOUND;
    static HttpStatus METHOD_NOT_ALLOWED_405 = HttpStatus.METHOD_NOT_ALLOWED;
    static HttpStatus UNPROCESSABLE_ENTITY_422 = HttpStatus.UNPROCESSABLE_ENTITY;
    static HttpStatus TOO_MANY_REQUESTS_429 = HttpStatus.TOO_MANY_REQUESTS;
    static HttpStatus INTERNAL_SERVER_ERROR_500 = HttpStatus.INTERNAL_SERVER_ERROR;
    static HttpStatus BAD_GATEWAY_502 = HttpStatus.BAD_GATEWAY;
    static HttpStatus SERVICE_UNAVAILABLE_503 = HttpStatus.SERVICE_UNAVAILABLE;
    static HttpStatus GATEWAY_TIMEOUT_504 = HttpStatus.GATEWAY_TIMEOUT;

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
