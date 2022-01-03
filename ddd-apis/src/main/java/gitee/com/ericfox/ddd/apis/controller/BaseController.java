package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserPagePram;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import org.springframework.http.ResponseEntity;

public interface BaseController {
    ResponseEntity<?> detail(Long id);

    ResponseEntity<?> page(SysUserPagePram param);

    ResponseEntity<?> list(SysUserPagePram param);

    ResponseEntity<?> create(SysUser sysUser);

    ResponseEntity<?> edit(SysUser sysUser);

    ResponseEntity<?> remove(SysUser sysUser);
}
