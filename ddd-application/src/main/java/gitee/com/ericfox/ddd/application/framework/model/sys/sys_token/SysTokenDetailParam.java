package gitee.com.ericfox.ddd.application.framework.model.sys.sys_token;

import gitee.com.ericfox.ddd.common.interfaces.apis.BaseDetailParam;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysTokenDetailParam implements BaseDetailParam<SysToken, SysTokenEntity> {
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