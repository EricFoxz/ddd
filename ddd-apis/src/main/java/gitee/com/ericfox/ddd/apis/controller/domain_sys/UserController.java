package gitee.com.ericfox.ddd.apis.controller.domain_sys;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.controller.BaseController;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserPagePram;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserAgg;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
@ResponseBody
public class UserController implements BaseController {
    @Resource
    SysUserService sysUserService;

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        SysUserAgg sysUserAgg = sysUserService.findById(id);
        return ResBuilder.defValue.success().put("data", sysUserAgg).build();
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResponseEntity<?> page(SysUserPagePram sysUserPagePram) {
        PageInfo<SysUserAgg> sysUserAggPageInfo = sysUserService.queryPage(sysUserPagePram.toParent(), sysUserPagePram.getPageNum(), sysUserPagePram.getPageSize());
        return ResBuilder.defValue.success().put("data", sysUserAggPageInfo).build();
    }

    @GetMapping("/list/{pageSize}")
    public ResponseEntity<?> list(SysUserPagePram sysUserPagePram) {
        List<SysUserAgg> sysUserAggs = sysUserService.queryList(sysUserPagePram.toParent(), sysUserPagePram.getPageSize());
        return ResBuilder.defValue.success().put("data", sysUserAggs).build();
    }

    @PutMapping("/create")
    public ResponseEntity<?> create() {
        return ResBuilder.defValue.created().build();
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> edit() {
        return ResBuilder.defValue.success().put("id", 1).build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> remove() {
        return ResBuilder.defValue.success().put("id", 1).build();
    }
}
