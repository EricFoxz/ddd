package gitee.com.ericfox.ddd.common.interfaces.apis;

import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseApiController<PAGE_PARAM extends BasePageParam, DETAIL_PARAM extends BaseDetailParam, DTO extends BaseDto> extends BaseHttpStatus {
    default ResponseEntity<?> detail(Long id) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> page(PAGE_PARAM pageParam) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> list(PAGE_PARAM pageParam) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> streamList(PAGE_PARAM pageParam) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> create(DETAIL_PARAM detailParam) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> edit(DETAIL_PARAM detailParam) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> remove(DETAIL_PARAM detailParam) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    default ResponseEntity<?> multiRemove(List<DETAIL_PARAM> detailParamList) {
        return Constants.getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
