package gitee.com.ericfox.ddd.domain.sys.model.sys_token;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseCondition;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysTokenEntityBase implements BaseEntity<SysToken, SysTokenEntity> {
    private static SysTokenService _sysTokenService;
    protected BaseCondition<?> _condition;
    protected SysToken _po;

    SysTokenContext.Rule _rule;
    SysTokenContext.Moment _moment;

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
    public synchronized SysTokenService service() {
        if (_sysTokenService == null) {
            _sysTokenService = SpringUtil.getBean(SysTokenService.class);
        }
        return _sysTokenService;
    }

    @Override
    public SysToken toPo() {
        if (_po == null) {
            _po = new SysToken();
        }
        BeanUtil.copyProperties(this, _po, Constants.IGNORE_NULL_VALUE_COPY_OPTIONS);
        return _po;
    }

    @Override
    public SysTokenEntity fromPo(SysToken _po) {
        this._po = _po;
        BeanUtil.copyProperties(_po, this, Constants.IGNORE_NULL_VALUE_COPY_OPTIONS);
        return (SysTokenEntity) this;
    }
}
