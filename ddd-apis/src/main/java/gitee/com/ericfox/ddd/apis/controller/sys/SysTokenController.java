package gitee.com.ericfox.ddd.apis.controller.sys;

import gitee.com.ericfox.ddd.apis.controller.sys.base.SysTokenControllerBase;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys/sysToken")
public class SysTokenController extends SysTokenControllerBase {
    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysTokenEntity entity) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysTokenEntity entity) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysTokenEntity entity) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

}
