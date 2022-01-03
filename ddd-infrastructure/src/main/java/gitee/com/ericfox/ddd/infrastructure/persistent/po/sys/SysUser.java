package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.OrmEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@OrmEnabledAnnotation
public class SysUser implements BasePo<SysUser> {
    private Long id;
    private String username;

    @Override
    public String tableName() {
        return "sys_user";
    }
}
