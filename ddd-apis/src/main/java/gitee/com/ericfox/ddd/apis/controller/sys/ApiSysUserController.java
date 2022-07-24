package gitee.com.ericfox.ddd.apis.controller.sys;

import gitee.com.ericfox.ddd.apis.controller.sys.base.ApiSysUserControllerBase;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysTokenDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysUserDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_user.SysUserDetailParam;
import gitee.com.ericfox.ddd.common.toolkit.trans.ResBuilder;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/sysUser")
@Slf4j
public class ApiSysUserController extends ApiSysUserControllerBase {
    @Resource
    private Mono<RSocketRequester> requesterMono;

    @Resource
    private SysUserService sysUserService;

    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysUserDetailParam detailParam) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysUserDetailParam detailParam) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysUserDetailParam detailParam) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    @Override
    @DeleteMapping("/multiRemove")
    public ResponseEntity<?> multiRemove(List<SysUserDetailParam> detailParamList) {
        return ResBuilder.noData().setStatus(METHOD_NOT_ALLOWED_405).build();
    }

    /**
     * 登录
     */
    @PutMapping("/login")
    public ResponseEntity<?> login(SysUserDetailParam detailParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".detail")
                            .data(detailParam);
                })
                .flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysTokenDto.class))
                .doOnNext((dto) -> {
                    if (dto != null && dto.getToken() != null) {
                        resBuilder.setData(dto);
                    } else {
                        resBuilder.setStatus(FORBIDDEN_403);
                    }
                }).block();
        return resBuilder.build();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(SysUserDetailParam detailParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success().setStatus(CREATED_201);
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".register")
                            .data(detailParam);
                })
                .flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysUserDto.class))
                .doOnNext((dto) -> {
                    if (dto != null) {
                        resBuilder.setData(dto);
                    } else {
                        resBuilder.setStatus(FORBIDDEN_403);
                    }
                }).block();
        return resBuilder.build();
    }
}
