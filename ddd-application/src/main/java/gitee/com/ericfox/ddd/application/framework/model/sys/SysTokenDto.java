package gitee.com.ericfox.ddd.application.framework.model.sys;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.application.framework.model.sys.base.SysTokenDtoBase;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SysTokenDto extends SysTokenDtoBase {
    @Override
    public SysTokenDto fromEntity(SysTokenEntity entity) {
        return super.fromEntity(entity);
    }

    @Override
    public List<SysTokenDto> fromEntityList(List<SysTokenEntity> entityList) {
        return super.fromEntityList(entityList);
    }

    @Override
    public List<SysTokenDto> fromEntities(SysTokenEntity... entities) {
        return super.fromEntities(entities);
    }

    @Override
    public PageInfo<SysTokenDto> fromEntityPage(PageInfo<SysTokenEntity> entityPage) {
        return super.fromEntityPage(entityPage);
    }
}