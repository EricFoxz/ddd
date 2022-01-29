package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserEntityBase implements BaseEntity<SysUser, SysUserEntity> {
    private static SysUserService sysUserService;
    private BaseCondition<?> _condition;
    private SysUser po;

    private Long id;

    public synchronized SysUserServiceBase getService() {
        if (sysUserService == null) {
            sysUserService = SpringUtil.getBean(SysUserService.class);
        }
        return sysUserService;
    }

    @Override
    public SysUser toPo() {
        if (po == null) {
            po = new SysUser();
            BeanUtil.copyProperties(this, po, false);
        }
        return po;
    }

    @Override
    public SysUserEntity fromPo(SysUser po) {
        this.po = po;
        return (SysUserEntity) this;
    }

    private void setPo(SysUser po) {
        this.po = po;
    }

    private SysUser getPo() {
        return po;
    }
}
