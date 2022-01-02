package gitee.com.ericfox.ddd.infrastructure.general.common.annos;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrmMysqlEnabledAnnotation {
    /**
     * 数据主键
     */
    String value() default "id";
}
