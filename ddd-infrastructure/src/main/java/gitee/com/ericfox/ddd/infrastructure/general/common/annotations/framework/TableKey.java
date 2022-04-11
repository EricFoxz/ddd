package gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework;

import gitee.com.ericfox.ddd.common.enums.db.MySqlTableKeyEnum;

public @interface TableKey {
    String[] value();

    MySqlTableKeyEnum type() default MySqlTableKeyEnum.PRIMARY_KEY;
}
