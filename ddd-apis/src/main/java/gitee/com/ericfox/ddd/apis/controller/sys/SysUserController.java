package gitee.com.ericfox.ddd.apis.controller.sys;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.controller.BaseController;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserPageParam;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserAgg;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
@ResponseBody
public class SysUserController implements BaseController<SysUser, SysUserEntity, SysUserPageParam, SysUserDetailParam> {
    @Resource
    SysUserService sysUserService;

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        SysUserAgg sysUserAgg = sysUserService.findById(id);
        return ResBuilder.defValue.success().put("data", sysUserAgg).build();
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResponseEntity<?> page(SysUserPageParam sysUserPagePram) {
        SysUserEntity sysUserEntity = sysUserPagePram.toEntity();
        sysUserEntity.set_condition(sysUserEntity.toCondition());
        PageInfo<SysUserAgg> sysUserAggPageInfo = sysUserService.queryPage(sysUserEntity, sysUserPagePram.getPageNum(), sysUserPagePram.getPageSize());
        return ResBuilder.defValue.success().put("data", sysUserAggPageInfo).build();
    }

    @Override
    @GetMapping("/list/{pageSize}")
    public ResponseEntity<?> list(SysUserPageParam param) {
        List<SysUserAgg> sysUserAggs = sysUserService.queryList(param.toEntity(), param.getPageSize());
        return ResBuilder.defValue.success().put("data", sysUserAggs).build();
    }

    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysUserEntity sysUser) {
        sysUserService.insert(sysUser);
        return ResBuilder.defValue.created().put("data", sysUser).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysUserEntity sysUser) {
        boolean b = sysUserService.update(sysUser);
        if (b) {
            return ResBuilder.defValue.success().put("id", sysUser.getId()).build();
        }
        return ResBuilder.defValue.noContent().build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysUserEntity sysUser) {
        boolean b = sysUserService.deleteById(sysUser);
        if (b) {
            return ResBuilder.defValue.success().put("id", sysUser.getId()).build();
        }
        return ResBuilder.defValue.noContent().build();
    }
}
