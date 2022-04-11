package gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableKeys {
    TableKey[] value();
}
