package gitee.com.ericfox.ddd.apis.controller.sys;

import gitee.com.ericfox.ddd.apis.assembler.Dto;
import gitee.com.ericfox.ddd.apis.controller.sys.base.SysUserControllerBase;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysTokenDto;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysUserDto;
import gitee.com.ericfox.ddd.application.framework.model.r_socket.RSocketMessageBean;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/sys/sysUser")
@Slf4j
public class SysUserController extends SysUserControllerBase {
    @Resource
    private Mono<RSocketRequester> requesterMono;
    @Resource
    private SysUserService sysUserService;

    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysUserEntity entity) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysUserEntity entity) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysUserEntity entity) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    /**
     * 登录
     */
    @PutMapping("/login")
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
    @PostMapping("/register")
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

    @GetMapping("/rsocket")
    @ResponseBody
    public String rsocket() {
        AtomicReference<String> response = new AtomicReference<>();
        requesterMono.map(rSocketRequester -> {
                    RSocketMessageBean messageBean = new RSocketMessageBean();
                    messageBean.setTitle("标题");
                    messageBean.setContent("内容");
                    return rSocketRequester.route("application.framework.controller.sys.ApplicationFrameWorkSysUserController.pong")
                            .data("1234");
                })
                .flatMap(retrieveSpec -> retrieveSpec.retrieveMono(String.class))
                .doOnNext(object -> {
                    log.info(object);
                    response.set(object);
                }).block();
        return response.get();
    }
}
