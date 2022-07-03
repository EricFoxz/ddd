package gitee.com.ericfox.ddd.apis.model.param.sys.sys_token;

import gitee.com.ericfox.ddd.common.interfaces.apis.BasePageParam;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenContext;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserContext;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysTokenPageParam implements BasePageParam<SysToken, SysTokenEntity, SysTokenContext.Description, SysTokenContext.Moment, SysTokenContext.Rule> {
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    SysTokenContext.Description _description;
    SysTokenContext.Moment _moment;
    SysTokenContext.Rule _rule;

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
    public SysTokenEntity toEntity() {
        SysTokenEntity entity = new SysTokenEntity();
        BeanUtil.copyProperties(this, entity, false);
        return entity;
    }
}
