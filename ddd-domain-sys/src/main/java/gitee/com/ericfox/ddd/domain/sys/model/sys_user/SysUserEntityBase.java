package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserEntityBase implements BaseEntity<SysUser, SysUserEntity> {
    private static SysUserService _sysUserService;
    protected BaseCondition<?> _condition;
    protected SysUser _po;

    SysUserContext.Rule _rule;
    SysUserContext.Moment _moment;

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
    public SysUser toPo() {
        if (_po == null) {
            _po = new SysUser();
        }
        BeanUtil.copyProperties(this, _po, Constants.IGNORE_NULL_VALUE_COPY_OPTIONS);
        return _po;
    }

    @Override
    public SysUserEntity fromPo(SysUser _po) {
        this._po = _po;
        BeanUtil.copyProperties(_po, this, Constants.IGNORE_NULL_VALUE_COPY_OPTIONS);
        return (SysUserEntity) this;
    }
}
