package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserPagePram;
import org.springframework.http.ResponseEntity;

public interface BaseController {
    ResponseEntity<?> detail(Long id);

    ResponseEntity<?> page(SysUserPagePram param);

    ResponseEntity<?> create();

    ResponseEntity<?> edit();

    ResponseEntity<?> remove();
}
