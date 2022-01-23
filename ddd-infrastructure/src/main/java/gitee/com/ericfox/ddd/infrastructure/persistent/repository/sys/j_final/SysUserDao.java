package gitee.com.ericfox.ddd.infrastructure.persistent.repository.sys.j_final;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.JFinalBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class SysUserDao extends JFinalBaseDao<SysUser, SysUserDao> {
    public static final SysUserDao dao = new SysUserDao().dao();

    private Long id;
    private String username;

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public Class<SysUser> poClass() {
        return SysUser.class;
    }
}
