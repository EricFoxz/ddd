package gitee.com.ericfox.ddd.infrastructure.persistent.dao.sys.my_sql_repo_strategy;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysContext;
import gitee.com.ericfox.ddd.infrastructure.service.repo.impl.JFinalBaseDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Setter
@Getter
public class SysContextDao extends JFinalBaseDao<SysContext, SysContextDao> {
    public static final SysContextDao dao = new SysContextDao().dao();
    private Serializable id;

    @Override
    public Class<SysContext> poClass() {
        return SysContext.class;
    }
}
