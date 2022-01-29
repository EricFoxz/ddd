package gitee.com.ericfox.ddd.apis.model.dto.sys;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.model.dto.sys.base.SysUserDtoBase;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;

import java.util.List;

public class SysUserDto extends SysUserDtoBase {
    @Override
    public SysUserDto fromEntity(SysUserEntity entity) {
        return super.fromEntity(entity);
    }

    @Override
    public List<SysUserDto> fromEntityList(List<SysUserEntity> sysUserEntities) {
        return super.fromEntityList(sysUserEntities);
    }

    @Override
    public List<SysUserDto> fromEntities(SysUserEntity... entities) {
        return super.fromEntities(entities);
    }

    @Override
    public PageInfo<SysUserDto> fromEntityPage(PageInfo<SysUserEntity> entityPage) {
        return super.fromEntityPage(entityPage);
    }
}