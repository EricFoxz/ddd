package gitee.com.ericfox.ddd.infrastructure.persistent.po;

import java.io.Serializable;

public interface BasePo<T extends BasePo<T>> extends Serializable {
    Serializable getId();
    String getTableName();
}
