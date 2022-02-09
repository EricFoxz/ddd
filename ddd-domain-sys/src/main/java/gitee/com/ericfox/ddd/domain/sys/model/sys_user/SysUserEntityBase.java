package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import cn.hutool.core.bean.copier.CopyOptions;
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
    protected BaseCondition<?> _condition;
    protected SysUser po;

    private Long id;
    /**
     * 用户名
     */
    private String username;
    private java.math.BigDecimal money;
    private String userInfo;

    @Override
    public synchronized SysUserService service() {
        if (sysUserService == null) {
            sysUserService = SpringUtil.getBean(SysUserService.class);
        }
        return sysUserService;
    }

    @Override
    public SysUser toPo() {
        if (po == null) {
            po = new SysUser();
        }
        BeanUtil.copyProperties(this, po, CopyOptions.create().ignoreNullValue());
        return po;
    }

    @Override
    public SysUserEntity fromPo(SysUser po) {
        this.po = po;
        return (SysUserEntity) this;
    }

    protected void setPo(SysUser po) {
        this.po = po;
    }

    protected SysUser getPo() {
        return po;
    }
}
