package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseAgg;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserAgg extends SysUserEntity implements BaseAgg<SysUserEntity, SysUserAgg> {

}