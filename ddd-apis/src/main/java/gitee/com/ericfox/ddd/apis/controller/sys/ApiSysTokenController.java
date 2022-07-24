package gitee.com.ericfox.ddd.apis.controller.sys;

import gitee.com.ericfox.ddd.apis.controller.sys.base.ApiSysTokenControllerBase;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_token.SysTokenDetailParam;
import gitee.com.ericfox.ddd.common.toolkit.trans.ResBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys/sysToken")
public class ApiSysTokenController extends ApiSysTokenControllerBase {
    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysTokenDetailParam detailParam) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysTokenDetailParam detailParam) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysTokenDetailParam detailParam) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

}
