package gitee.com.ericfox.ddd.apis.controller.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysUserDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_user.SysUserDetailParam;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_user.SysUserPageParam;
import gitee.com.ericfox.ddd.common.interfaces.apis.BaseApiController;
import gitee.com.ericfox.ddd.common.toolkit.coding.JSONUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@ResponseBody
public abstract class ApiSysUserControllerBase implements BaseApiController<SysUserPageParam, SysUserDetailParam, SysUserDto> {
    @Resource
    private Mono<RSocketRequester> requesterMono;

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".detail")
                            .data(id);
                })
                .flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysUserDto.class))
                .doOnNext((dto) -> {
                    log.info(JSONUtil.toJsonStr(dto));
                    resBuilder.setData(dto);
                }).block();
        return resBuilder.build();
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResponseEntity<?> page(SysUserPageParam pageParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".page")
                            .data(pageParam);
                }).flatMap(retrieveSpec -> retrieveSpec.retrieveMono(PageInfo.class))
                .doOnNext((pageInfo -> {
                    log.info(JSONUtil.toJsonStr(pageInfo));
                    resBuilder.setData(pageInfo);
                })).block();
        return resBuilder.build();
    }

    @Override
    @GetMapping("/list/{pageSize}")
    public ResponseEntity<?> list(SysUserPageParam pageParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".list")
                            .data(pageParam);
                }).flatMap(retrieveSpec -> retrieveSpec.retrieveMono(List.class))
                .doOnNext((list -> {
                    log.info(JSONUtil.toJsonStr(list));
                    resBuilder.setData(list);
                })).block();
        return resBuilder.build();
    }

    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysUserDetailParam detailParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".create")
                            .data(detailParam);
                }).flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysUserDto.class))
                .doOnNext((dto -> {
                    log.info(JSONUtil.toJsonStr(dto));
                    resBuilder.setData(dto);
                })).block();
        return resBuilder.build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysUserDetailParam detailParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".edit")
                            .data(detailParam);
                }).flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysUserDto.class))
                .doOnNext((dto -> {
                    log.info(JSONUtil.toJsonStr(dto));
                    resBuilder.setData(dto);
                })).block();
        return resBuilder.build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysUserDetailParam detailParam) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".remove")
                            .data(detailParam);
                }).flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysUserDto.class))
                .doOnNext((dto -> {
                    log.info(JSONUtil.toJsonStr(dto));
                    resBuilder.setData(dto);
                })).block();
        return resBuilder.build();
    }

    @Override
    @DeleteMapping("/multiRemove")
    public ResponseEntity<?> multiRemove(List<SysUserDetailParam> detailParamList) {
        ResBuilder resBuilder = ResBuilder.defValue.success();
        requesterMono.map(rSocketRequester -> {
                    return rSocketRequester.route(SysUserDto.BUS_NAME + ".multiRemove")
                            .data(detailParamList);
                }).flatMap(retrieveSpec -> retrieveSpec.retrieveMono(SysUserDto.class))
                .doOnNext((dto -> {
                    log.info(JSONUtil.toJsonStr(dto));
                    resBuilder.setData(dto);
                })).block();
        return resBuilder.build();
    }
}
