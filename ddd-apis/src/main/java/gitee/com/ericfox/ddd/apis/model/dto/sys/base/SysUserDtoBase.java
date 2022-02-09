package gitee.com.ericfox.ddd.apis.model.dto.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysUserDto;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDto;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public abstract class SysUserDtoBase implements BaseDto<SysUser, SysUserEntity, SysUserDto>, Serializable {
    private Long id;
    private String username;

    public SysUserDto fromEntity(SysUserEntity entity) {
        SysUserDto dto = new SysUserDto();
        BeanUtil.copyProperties(entity, dto, false);
        return dto;
    }

    public List<SysUserDto> fromEntityList(List<SysUserEntity> entityList) {
        return BeanUtil.copyToList(entityList, SysUserDto.class);
    }

    public List<SysUserDto> fromEntities(SysUserEntity... entities) {
        return fromEntityList(CollUtil.newArrayList(entities));
    }

    public PageInfo<SysUserDto> fromEntityPage(PageInfo<SysUserEntity> entityPage) {
        PageInfo<SysUserDto> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(entityPage.getPageNum());
        pageInfo.setPageSize(entityPage.getPageSize());
        pageInfo.setTotal(entityPage.getTotal());
        pageInfo.setList(fromEntityList(entityPage.getList()));
        return pageInfo;
    }
}
