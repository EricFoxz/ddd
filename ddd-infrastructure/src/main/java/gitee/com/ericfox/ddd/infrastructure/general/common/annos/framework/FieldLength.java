package gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 字段长度
 */
@Target(ElementType.FIELD)
public @interface FieldLength {
    int value();
}
