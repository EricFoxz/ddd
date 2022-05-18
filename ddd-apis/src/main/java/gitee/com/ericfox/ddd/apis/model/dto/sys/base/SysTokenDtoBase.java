package gitee.com.ericfox.ddd.apis.model.dto.sys.base;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.apis.model.dto.sys.SysTokenDto;
import gitee.com.ericfox.ddd.common.interfaces.apis.BaseDto;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class SysTokenDtoBase implements BaseDto<SysToken, SysTokenEntity, SysTokenDto> {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 平台
     */
    private String platform;
    /**
     * 令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 过期时间
     */
    private Long expireDate;
    /**
     * 创建时间
     */
    private Long createDate;

    @Override
    public SysTokenDto fromEntity(SysTokenEntity entity) {
            SysTokenDto dto = new SysTokenDto();
        BeanUtil.copyProperties(entity, dto, false);
        return dto;
    }

    @Override
    public List<SysTokenDto> fromEntityList(List<SysTokenEntity> entityList) {
        return BeanUtil.copyToList(entityList, SysTokenDto.class);
    }

    @Override
    public List<SysTokenDto> fromEntities(SysTokenEntity... entities) {
        return fromEntityList(CollUtil.newArrayList(entities));
    }

    @Override
    public PageInfo<SysTokenDto> fromEntityPage(PageInfo<SysTokenEntity> entityPage) {
        PageInfo<SysTokenDto> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(entityPage.getPageNum());
        pageInfo.setPageSize(entityPage.getPageSize());
        pageInfo.setTotal(entityPage.getTotal());
        pageInfo.setList(fromEntityList(entityPage.getList()));
        return pageInfo;
    }
}
