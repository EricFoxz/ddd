package gitee.com.ericfox.ddd.domain.sys.model.sys_token;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;

public class SysTokenEntity extends SysTokenEntityBase {
    @Override
    public SysToken toPo() {
        return super.toPo();
    }

    @Override
    public SysTokenEntity fromPo(SysToken po) {
        return super.fromPo(po);
    }
}
