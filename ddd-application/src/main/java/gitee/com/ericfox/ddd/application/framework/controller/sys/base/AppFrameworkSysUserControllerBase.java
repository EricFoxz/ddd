package gitee.com.ericfox.ddd.application.framework.controller.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysUserDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_user.SysUserDetailParam;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_user.SysUserPageParam;
import gitee.com.ericfox.ddd.common.interfaces.application.BaseController;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.common.toolkit.trans.Dto;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@ResponseBody
public abstract class AppFrameworkSysUserControllerBase implements BaseController<SysUser, SysUserEntity, SysUserPageParam, SysUserDetailParam, SysUserDto> {
    @Resource
    SysUserService sysUserService;

    @MessageMapping(SysUserDto.BUS_NAME + ".detail")
    public Mono<SysUserDto> detail(@PathVariable Long id) {
        return Mono.just(Dto.fromEntity(SysUserDto.class, sysUserService.findById(id)));
    }

    @MessageMapping(SysUserDto.BUS_NAME + ".page")
    public Mono<PageInfo<SysUserDto>> page(SysUserPageParam pageParam) {
        SysUserEntity entity = pageParam.toEntity();
        entity.set_condition(entity.toCondition());
        return Mono.just(Dto.fromEntityPage(SysUserDto.class, sysUserService.queryPage(entity, pageParam.getPageNum(), pageParam.getPageSize())));
    }

    @Override
    @MessageMapping(SysUserDto.BUS_NAME + ".list")
    public Mono<List<SysUserDto>> list(SysUserPageParam pageParam) {
        List<SysUserEntity> sysUserEntityList = sysUserService.queryList(pageParam.toEntity(), pageParam.getPageSize());
        return Mono.just(Dto.fromEntityList(SysUserDto.class, sysUserEntityList));
    }

    @Override
    @MessageMapping(SysUserDto.BUS_NAME + ".create")
    public Mono<SysUserDto> create(@RequestBody SysUserDetailParam detailParam) {
        SysUserEntity entity = detailParam.toEntity();
        sysUserService.insert(entity);
        SysUserDto dto = Dto.fromEntity(SysUserDto.class, entity);
        return Mono.just(Dto.fromEntity(SysUserDto.class, detailParam.toEntity()));
    }

    @Override
    @MessageMapping(SysUserDto.BUS_NAME + ".edit")
    public Mono<SysUserDto> edit(@RequestBody SysUserDetailParam detailParam) {
        SysUserEntity entity = detailParam.toEntity();
        boolean b = sysUserService.update(entity);
        if (b) {
            return Mono.just(Dto.fromEntity(SysUserDto.class, detailParam.toEntity()));
        }
        return null;
    }

    @Override
    @MessageMapping(SysUserDto.BUS_NAME + ".remove")
    public Mono<SysUserDto> remove(SysUserDetailParam detailParam) {
        SysUserEntity entity = detailParam.toEntity();
        boolean b = sysUserService.deleteById(entity);
        if (b) {
            return Mono.just(Dto.fromEntity(SysUserDto.class, detailParam.toEntity()));
        }
        return null;
    }

    @Override
    @MessageMapping(SysUserDto.BUS_NAME + ".multiRemove")
    public Mono<List<SysUserDto>> multiRemove(List<SysUserDetailParam> paramList) {
        List<SysUserEntity> entityList = CollUtil.newArrayList();
        for (SysUserDetailParam detailParam : paramList) {
            entityList.add(detailParam.toEntity());
        }
        boolean b = sysUserService.multiDeleteById(entityList);
        if (b) {
            return Mono.just(Dto.fromEntityList(SysUserDto.class, entityList));
        }
        return Mono.just(CollUtil.newArrayList());
    }
}
