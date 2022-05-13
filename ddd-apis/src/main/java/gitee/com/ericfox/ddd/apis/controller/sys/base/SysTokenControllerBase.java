package gitee.com.ericfox.ddd.apis.controller.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.assembler.Dto;
import gitee.com.ericfox.ddd.apis.controller.BaseController;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysTokenDto;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_token.SysTokenDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_token.SysTokenPageParam;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@ResponseBody
public abstract class SysTokenControllerBase implements BaseController<SysToken, SysTokenEntity, SysTokenPageParam, SysTokenDetailParam> {
    @Resource
        SysTokenService sysTokenService;

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
            SysTokenDto dto = Dto.fromEntity(SysTokenDto.class, sysTokenService.findById(id));
        return ResBuilder.defValue.success().setData(dto).build();
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResponseEntity<?> page(SysTokenPageParam pageParam) {
            SysTokenEntity entity = pageParam.toEntity();
        entity.set_condition(entity.toCondition());
        PageInfo<SysTokenDto> pageInfo = Dto.fromEntityPage(SysTokenDto.class, sysTokenService.queryPage(entity, pageParam.getPageNum(), pageParam.getPageSize()));
        return ResBuilder.defValue.success().setData(pageInfo).build();
    }

    @Override
    @GetMapping("/list/{pageSize}")
    public ResponseEntity<?> list(SysTokenPageParam pageParam) {
        List<SysTokenEntity> sysTokenEntityList = sysTokenService.queryList(pageParam.toEntity(), pageParam.getPageSize());
        List<SysTokenDto> list = Dto.fromEntityList(SysTokenDto.class, sysTokenEntityList);
        return ResBuilder.defValue.success().setData(list).build();
    }

    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysTokenEntity entity) {
            sysTokenService.insert(entity);
            SysTokenDto dto = Dto.fromEntity(SysTokenDto.class, entity);
        return ResBuilder.defValue.created().setData(dto).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysTokenEntity entity) {
        boolean b = sysTokenService.update(entity);
        if (b) {
            return ResBuilder.defValue.success().putIntoData("id", entity.getId()).build();
        }
        return ResBuilder.defValue.noContent().build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysTokenEntity entity) {
        boolean b = sysTokenService.deleteById(entity);
        if (b) {
            return ResBuilder.defValue.success().putIntoData("id", entity.getId()).build();
        }
        return ResBuilder.defValue.noContent().build();
    }

    @Override
    @DeleteMapping("/multiRemove")
    public ResponseEntity<?> multiRemove(List<SysTokenEntity> entityList) {
        boolean b = sysTokenService.multiDeleteById(entityList);
        if (b) {
            return ResBuilder.defValue.success().put("data", entityList).build();
        }
        return ResBuilder.defValue.noContent().build();
    }
}
