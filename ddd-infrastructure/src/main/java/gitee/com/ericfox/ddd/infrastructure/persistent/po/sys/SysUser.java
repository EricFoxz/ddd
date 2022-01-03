package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.CacheEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.OrmMysqlEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@OrmMysqlEnabledAnnotation
@CacheEnabledAnnotation
public class SysUser implements BasePo<SysUser> {
    private Long id;
    private String username;

    @Override
    public String getTableName() {
        return "sys_user";
    }
}
