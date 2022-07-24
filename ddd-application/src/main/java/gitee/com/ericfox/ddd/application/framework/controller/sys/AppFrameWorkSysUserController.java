package gitee.com.ericfox.ddd.application.framework.controller.sys;

import gitee.com.ericfox.ddd.application.framework.controller.sys.base.AppFrameworkSysUserControllerBase;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysTokenDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysUserDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_user.SysUserDetailParam;
import gitee.com.ericfox.ddd.common.toolkit.trans.Dto;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Controller
public class AppFrameWorkSysUserController extends AppFrameworkSysUserControllerBase {
    @Resource
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @MessageMapping(SysUserDto.BUS_NAME + ".login")
    public Mono<SysTokenDto> login(SysUserDetailParam detailParam) {
        SysTokenEntity token = sysUserService.login(detailParam.toEntity());
        if (token != null && token.getToken() != null) {
            return Mono.just(Dto.fromEntity(SysTokenDto.class, token));
        }
        return Mono.empty();
    }

    /**
     * 注册
     */
    @MessageMapping(SysUserDto.BUS_NAME + ".register")
    public Mono<SysUserDto> register(SysUserDetailParam detailParam) {
        SysUserEntity entity = sysUserService.register(detailParam.toEntity());
        if (entity != null) {
            return Mono.just(Dto.fromEntity(SysUserDto.class, entity));
        }
        return Mono.empty();
    }
}
