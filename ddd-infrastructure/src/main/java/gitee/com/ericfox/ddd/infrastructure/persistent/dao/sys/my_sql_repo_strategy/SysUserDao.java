package gitee.com.ericfox.ddd.infrastructure.persistent.dao.sys.my_sql_repo_strategy;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.JFinalBaseDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Setter
@Getter
public class SysUserDao extends JFinalBaseDao<SysUser, SysUserDao> {
    public static final SysUserDao dao = new SysUserDao().dao();
    private Serializable id;

    @Override
    public Class<SysUser> poClass() {
        return SysUser.class;
    }
}
