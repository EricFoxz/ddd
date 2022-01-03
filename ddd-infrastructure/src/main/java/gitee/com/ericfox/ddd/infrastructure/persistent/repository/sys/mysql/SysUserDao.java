package gitee.com.ericfox.ddd.infrastructure.persistent.repository.sys.mysql;

import com.jfinal.plugin.activerecord.Model;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserDao extends Model<SysUserDao> {
    public static final SysUserDao dao = new SysUserDao().dao();
}
