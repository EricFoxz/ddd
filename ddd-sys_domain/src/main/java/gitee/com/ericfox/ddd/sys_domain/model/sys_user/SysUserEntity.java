package gitee.com.ericfox.ddd.sys_domain.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.sys_domain.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserEntity extends SysUser implements BaseEntity {
    @Override
    public int create() {
        return 0;
    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    public int edit() {
        return 0;
    }
}
