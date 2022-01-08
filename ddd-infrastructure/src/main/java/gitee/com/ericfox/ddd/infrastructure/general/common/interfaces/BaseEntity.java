package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;

public interface BaseEntity<T extends SysUser> {
    BaseService<T> getDao();

    boolean create();

    boolean delete();

    boolean edit();
}
