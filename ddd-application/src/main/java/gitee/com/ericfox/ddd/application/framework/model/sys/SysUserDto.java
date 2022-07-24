package gitee.com.ericfox.ddd.application.framework.model.sys;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.application.framework.model.sys.base.SysUserDtoBase;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SysUserDto extends SysUserDtoBase {
    @Override
    public SysUserDto fromEntity(SysUserEntity entity) {
        return super.fromEntity(entity);
    }

    @Override
    public List<SysUserDto> fromEntityList(List<SysUserEntity> entityList) {
        return super.fromEntityList(entityList);
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