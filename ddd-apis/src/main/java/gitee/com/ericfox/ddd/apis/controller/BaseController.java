package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.apis.model.param.BaseDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.BasePageParam;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import org.springframework.http.ResponseEntity;

public interface BaseController<T extends BasePo<T>, V extends BaseEntity<T, V>, M extends BasePageParam<T, V>, N extends BaseDetailParam<T, V>> {
    ResponseEntity<?> detail(Long id);

    ResponseEntity<?> page(M param);

    ResponseEntity<?> list(M param);

    ResponseEntity<?> create(V v);

    ResponseEntity<?> edit(V v);

    ResponseEntity<?> remove(V v);
}
