package gitee.com.ericfox.ddd.apis.controller.sys;

import gitee.com.ericfox.ddd.apis.controller.sys.base.SysUserControllerBase;
import gitee.com.ericfox.ddd.common.toolkit.coding.SecureUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.common.toolkit.trans.SimpleCondition;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController extends SysUserControllerBase {
    @Resource
    private SysUserService sysUserService;

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

    @PutMapping("/login")
    public ResponseEntity<?> login(SysUserEntity sysUserEntity) {
        String token = sysUserService.login(sysUserEntity);
        if(StrUtil.isNotBlank(token)) {
            return ResBuilder.hashMapData()
                    .putIntoData("token", token)
                    .setStatus(200).build();
        }
        return ResBuilder.hashMapData().setStatus(401).build();
    }
}
