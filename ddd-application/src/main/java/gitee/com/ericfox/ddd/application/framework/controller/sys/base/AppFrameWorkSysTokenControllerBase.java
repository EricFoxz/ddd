package gitee.com.ericfox.ddd.application.framework.controller.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.application.framework.model.sys.SysTokenDto;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_token.SysTokenDetailParam;
import gitee.com.ericfox.ddd.application.framework.model.sys.sys_token.SysTokenPageParam;
import gitee.com.ericfox.ddd.common.interfaces.application.BaseController;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.common.toolkit.trans.Dto;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenService;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/sysToken")
public class AppFrameWorkSysTokenControllerBase implements BaseController<SysToken, SysTokenEntity, SysTokenPageParam, SysTokenDetailParam, SysTokenDto> {
    @Resource
    SysTokenService sysTokenService;

    @MessageMapping(SysTokenDto.BUS_NAME + ".detail")
    public Mono<SysTokenDto> detail(@PathVariable Long id) {
        return Mono.just(Dto.fromEntity(SysTokenDto.class, sysTokenService.findById(id)));
    }

    @MessageMapping(SysTokenDto.BUS_NAME + ".page")
    public Mono<PageInfo<SysTokenDto>> page(SysTokenPageParam pageParam) {
        SysTokenEntity entity = pageParam.toEntity();
        entity.set_condition(entity.toCondition());
        return Mono.just(Dto.fromEntityPage(SysTokenDto.class, sysTokenService.queryPage(entity, pageParam.getPageNum(), pageParam.getPageSize())));
    }

    @Override
    @MessageMapping(SysTokenDto.BUS_NAME + ".list")
    public Mono<List<SysTokenDto>> list(SysTokenPageParam pageParam) {
        List<SysTokenEntity> sysUserEntityList = sysTokenService.queryList(pageParam.toEntity(), pageParam.getPageSize());
        return Mono.just(Dto.fromEntityList(SysTokenDto.class, sysUserEntityList));
    }

    @Override
    @MessageMapping(SysTokenDto.BUS_NAME + ".create")
    public Mono<SysTokenDto> create(@RequestBody SysTokenDetailParam detailParam) {
        SysTokenEntity entity = detailParam.toEntity();
        sysTokenService.insert(entity);
        return Mono.just(Dto.fromEntity(SysTokenDto.class, entity));
    }

    @Override
    @MessageMapping(SysTokenDto.BUS_NAME + ".edit")
    public Mono<SysTokenDto> edit(@RequestBody SysTokenDetailParam detailParam) {
        SysTokenEntity entity = detailParam.toEntity();
        boolean b = sysTokenService.update(entity);
        if (b) {
            return Mono.just(Dto.fromEntity(SysTokenDto.class, entity));
        }
        return null;
    }

    @Override
    @MessageMapping(SysTokenDto.BUS_NAME + ".remove")
    public Mono<SysTokenDto> remove(SysTokenDetailParam detailParam) {
        SysTokenEntity entity = detailParam.toEntity();
        boolean b = sysTokenService.deleteById(entity);
        if (b) {
            return Mono.just(Dto.fromEntity(SysTokenDto.class, entity));
        }
        return null;
    }

    @Override
    @MessageMapping(SysTokenDto.BUS_NAME + ".multiRemove")
    public Mono<List<SysTokenDto>> multiRemove(List<SysTokenDetailParam> paramList) {
        List<SysTokenEntity> entityList = CollUtil.newArrayList();
        for (SysTokenDetailParam detailParam : paramList) {
            entityList.add(detailParam.toEntity());
        }
        boolean b = sysTokenService.multiDeleteById(entityList);
        if (b) {
            Dto.fromEntityList(SysTokenDto.class, entityList);
        }
        return Mono.just(CollUtil.newArrayList());
    }
}
