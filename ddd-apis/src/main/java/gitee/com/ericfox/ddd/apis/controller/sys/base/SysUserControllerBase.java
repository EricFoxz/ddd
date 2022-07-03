package gitee.com.ericfox.ddd.apis.controller.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.assembler.Dto;
import gitee.com.ericfox.ddd.apis.controller.BaseController;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysUserDto;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserDetailParam;
import gitee.com.ericfox.ddd.apis.model.param.sys.sys_user.SysUserPageParam;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@ResponseBody
public abstract class SysUserControllerBase implements BaseController<SysUser, SysUserEntity, SysUserPageParam, SysUserDetailParam> {
    @Resource
    SysUserService sysUserService;

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        SysUserDto dto = Dto.fromEntity(SysUserDto.class, sysUserService.findById(id));
        return ResBuilder.defValue.success().setData(dto).build();
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResponseEntity<?> page(SysUserPageParam pageParam) {
        SysUserEntity entity = pageParam.toEntity();
        entity.set_condition(entity.toCondition());
        PageInfo<SysUserDto> pageInfo = Dto.fromEntityPage(SysUserDto.class, sysUserService.queryPage(entity, pageParam.getPageNum(), pageParam.getPageSize()));
        return ResBuilder.defValue.success().setData(pageInfo).build();
    }

    @Override
    @GetMapping("/list/{pageSize}")
    public ResponseEntity<?> list(SysUserPageParam pageParam) {
        List<SysUserEntity> sysUserEntityList = sysUserService.queryList(pageParam.toEntity(), pageParam.getPageSize());
        List<SysUserDto> list = Dto.fromEntityList(SysUserDto.class, sysUserEntityList);
        return ResBuilder.defValue.success().setData(list).build();
    }

    @Override
    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody SysUserEntity entity) {
        sysUserService.insert(entity);
        SysUserDto dto = Dto.fromEntity(SysUserDto.class, entity);
        return ResBuilder.defValue.created().setData(dto).build();
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<?> edit(SysUserEntity entity) {
        boolean b = sysUserService.update(entity);
        if (b) {
            return ResBuilder.defValue.success().putIntoData("id", entity.getId()).build();
        }
        return ResBuilder.defValue.noContent().build();
    }

    @Override
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(SysUserEntity entity) {
        boolean b = sysUserService.deleteById(entity);
        if (b) {
            return ResBuilder.defValue.success().putIntoData("id", entity.getId()).build();
        }
        return ResBuilder.defValue.noContent().build();
    }

    @Override
    @DeleteMapping("/multiRemove")
    public ResponseEntity<?> multiRemove(List<SysUserEntity> entityList) {
        boolean b = sysUserService.multiDeleteById(entityList);
        if (b) {
            return ResBuilder.defValue.success().put("data", entityList).build();
        }
        return ResBuilder.defValue.noContent().build();
    }
}
