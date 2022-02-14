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
public abstract class SysUserEntityBase implements BaseEntity<SysUser, SysUserEntity> {
    private static SysUserService _sysUserService;
    protected BaseCondition<?> _condition;
    protected SysUser _po;

    SysUserContext.Rule _rule;
    SysUserContext.Moment _moment;
    SysUserContext.Description _description;

    private Long id;
    /**
     * 用户名
     */
    private String username;
    private java.math.BigDecimal money;
    private String userInfo;

    @Override
    public synchronized SysUserService service() {
        if (_sysUserService == null) {
            _sysUserService = SpringUtil.getBean(SysUserService.class);
        }
        return _sysUserService;
    }

    @Override
    public void run() {
        _description.apply(this);
    }

    @Override
    public SysUser toPo() {
        if (_po == null) {
            _po = new SysUser();
        }
        BeanUtil.copyProperties(this, _po, CopyOptions.create().ignoreNullValue());
        return _po;
    }

    @Override
    public SysUserEntity fromPo(SysUser po) {
        this._po = po;
        return (SysUserEntity) this;
    }
}
