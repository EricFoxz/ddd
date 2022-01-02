package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.OrmMysqlEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@OrmMysqlEnabledAnnotation
public class SysUser implements BasePo {
    private Long id;
    private String username;
}
