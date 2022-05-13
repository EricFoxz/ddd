package gitee.com.ericfox.ddd.infrastructure.persistent.dao.sys.my_sql_repo_strategy;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import gitee.com.ericfox.ddd.infrastructure.service.repo.impl.JFinalBaseDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Setter
@Getter
public class SysTokenDao extends JFinalBaseDao<SysToken, SysTokenDao> {
    public static final SysTokenDao dao = new SysTokenDao().dao();
    private Serializable id;

    @Override
    public Class<SysToken> poClass() {
        return SysToken.class;
    }
}
