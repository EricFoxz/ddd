package gitee.com.ericfox.ddd.apis.model.param.sys.sys_user;

import gitee.com.ericfox.ddd.common.interfaces.apis.BaseDetailParam;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserContext;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserDetailParam implements BaseDetailParam<SysUser, SysUserEntity, SysUserContext.Description, SysUserContext.Moment, SysUserContext.Rule> {
    SysUserContext.Description _description;
    SysUserContext.Moment _moment;
    SysUserContext.Rule _rule;

    /**
     * 主键
     */
    private Long id;
    /**
     * uuid可以存储第三方主键
     */
    private String uuid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 平台
     */
    private String platform;
    /**
     * 用户信息
     */
    private String userinfo;
    /**
     * 状态
     */
    private String statusEnum;
    /**
     * 创建日期
     */
    private java.sql.Timestamp createDate;

    @Override
    public SysUserEntity toEntity() {
        SysUserEntity entity = new SysUserEntity();
        BeanUtil.copyProperties(this, entity, false);
        return entity;
    }
}
