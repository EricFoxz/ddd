package gitee.com.ericfox.ddd.infrastructure.persistent.repository.sys.lucene;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy.LuceneFieldKey;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.LuceneBaseEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserDao extends SysUser implements LuceneBaseEntity<SysUser> {
    @LuceneFieldKey(type = LuceneFieldTypeEnum.LONG_POINT, needSort = true)
    private Long id;

    @Override
    public SysUser parent() {
        return new SysUser();
    }
}
