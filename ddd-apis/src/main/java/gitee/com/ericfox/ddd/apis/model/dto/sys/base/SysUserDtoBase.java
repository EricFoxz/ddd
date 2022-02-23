package gitee.com.ericfox.ddd.apis.model.dto.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysUserDto;
import gitee.com.ericfox.ddd.common.interfaces.BaseDto;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class SysUserDtoBase implements BaseDto<SysUser, SysUserEntity, SysUserDto> {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    private java.math.BigDecimal money;
    private String userInfo;

    @Override
    public SysUserDto fromEntity(SysUserEntity entity) {
        SysUserDto dto = new SysUserDto();
        BeanUtil.copyProperties(entity, dto, false);
        return dto;
    }

    @Override
    public List<SysUserDto> fromEntityList(List<SysUserEntity> entityList) {
        return BeanUtil.copyToList(entityList, SysUserDto.class);
    }

    @Override
    public List<SysUserDto> fromEntities(SysUserEntity... entities) {
        return fromEntityList(CollUtil.newArrayList(entities));
    }

    @Override
    public PageInfo<SysUserDto> fromEntityPage(PageInfo<SysUserEntity> entityPage) {
        PageInfo<SysUserDto> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(entityPage.getPageNum());
        pageInfo.setPageSize(entityPage.getPageSize());
        pageInfo.setTotal(entityPage.getTotal());
        pageInfo.setList(fromEntityList(entityPage.getList()));
        return pageInfo;
    }
}
