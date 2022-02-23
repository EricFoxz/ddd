package gitee.com.ericfox.ddd.apis.controller.sys;

import gitee.com.ericfox.ddd.apis.controller.sys.base.SysUserControllerBase;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys/user")
public class SysUserController extends SysUserControllerBase {
    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysUserEntity entity) {
        return ResBuilder.noData().setStatus(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysUserEntity entity) {
        return ResBuilder.noData().setStatus(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysUserEntity entity) {
        return ResBuilder.noData().setStatus(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
