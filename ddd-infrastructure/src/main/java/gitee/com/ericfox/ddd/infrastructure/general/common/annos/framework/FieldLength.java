package gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework;

import java.lang.annotation.*;

/**
 * 字段长度
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldLength {
    int length();
    int scale();
}
