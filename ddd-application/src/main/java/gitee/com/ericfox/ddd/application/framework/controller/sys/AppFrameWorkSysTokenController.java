package gitee.com.ericfox.ddd.application.framework.controller.sys;

import gitee.com.ericfox.ddd.application.framework.controller.sys.base.AppFrameWorkSysTokenControllerBase;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/sysToken")
public class AppFrameWorkSysTokenController extends AppFrameWorkSysTokenControllerBase {
    @Resource
    private SysTokenService sysTokenService;
}
