package gitee.com.ericfox.ddd.infrastructure.persistent.repository.sys.lucene;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy.LuceneFieldKey;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.LuceneBaseDao;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserDao extends SysUser implements LuceneBaseDao<SysUser> {
    @LuceneFieldKey(type = LuceneFieldTypeEnum.LONG_POINT, needSort = true)
    private Long id;
    @LuceneFieldKey(type = LuceneFieldTypeEnum.STRING_FIELD, needSort = true)
    private String username;

    @Override
    public Class<SysUser> poClass() {
        return SysUser.class;
    }
}
