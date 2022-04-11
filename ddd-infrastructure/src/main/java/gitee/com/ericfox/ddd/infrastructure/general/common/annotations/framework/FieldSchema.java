package gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework;

import gitee.com.ericfox.ddd.common.enums.contants.BooleanEnums;
import gitee.com.ericfox.ddd.common.enums.db.MySqlDataTypeEnum;

import java.lang.annotation.*;

/**
 * 字段结构定义
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldSchema {
    /**
     * 数据类型
     */
    MySqlDataTypeEnum dataType();

    /**
     * 长度
     */
    int length() default 0;

    /**
     * 精度
     */
    int scale() default 0;

    /**
     * 能否为空
     */
    BooleanEnums.EnglishCode isNullable() default BooleanEnums.EnglishCode.YES;

    /**
     * 默认值
     */
    String[] defaultValue() default {};

    /**
     * 备注
     */
    String comment() default "";
}
