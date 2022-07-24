package gitee.com.ericfox.ddd.application.framework.controller.sys;

import gitee.com.ericfox.ddd.application.framework.controller.sys.base.AppFrameworkSysUserControllerBase;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysTokenDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysUserDto;
import gitee.com.ericfox.ddd.common.toolkit.trans.Dto;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
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
    public ResponseEntity<?> login(SysUserEntity sysUserEntity) {
        SysTokenEntity token = sysUserService.login(sysUserEntity);
        if (token != null && token.getToken() != null) {
            return ResBuilder.hashMapData()
                    .setData(Dto.fromEntity(SysTokenDto.class, token))
                    .setStatus(OK_200).build();
        }
        return ResBuilder.hashMapData().setStatus(FORBIDDEN_403).build();
    }

    /**
     * 注册
     */
    @MessageMapping(SysUserDto.BUS_NAME + ".register")
    public ResponseEntity<?> register(SysUserEntity sysUserEntity) {
        SysUserEntity entity = sysUserService.register(sysUserEntity);
        if (entity != null) {
            return ResBuilder.hashMapData()
                    .setData(Dto.fromEntity(SysUserDto.class, sysUserEntity))
                    .setStatus(CREATED_201).build();
        }
        return ResBuilder.hashMapData()
                .setStatus(FORBIDDEN_403).build();
    }

    @MessageMapping(SysUserDto.BUS_NAME + ".pong")
    public Mono<String> pong(String pong) {
        return Mono.just("ECHO >> " + pong);
    }

    @MessageMapping(SysUserDto.BUS_NAME + ".echo")
    public Flux<String> echo(String echo) {
        return Flux.fromStream(echo.codePoints().mapToObj(msg -> {
            System.out.println("ECHO >> " + msg);
            return null;
        }));
    }
}
