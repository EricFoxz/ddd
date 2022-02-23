package gitee.com.ericfox.ddd.apis.model.param.sys.sys_user;

import gitee.com.ericfox.ddd.apis.model.param.BasePageParam;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserPageParam implements BasePageParam<SysUser, SysUserEntity> {
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    private Long id;
    /**
     * 用户名
     */
    private String username;
    private java.math.BigDecimal money;
    private String userInfo;

    @Override
    public SysUserEntity toEntity() {
        SysUserEntity entity = new SysUserEntity();
        BeanUtil.copyProperties(this, entity, false);
        return entity;
    }
}
