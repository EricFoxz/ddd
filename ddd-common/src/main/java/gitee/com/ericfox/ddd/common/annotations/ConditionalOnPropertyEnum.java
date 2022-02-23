package gitee.com.ericfox.ddd.common.annotations;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 根据枚举判断是否注入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional({OnPropertyEnumCondition.class})
public @interface ConditionalOnPropertyEnum {
    /**
     * 配置文件name
     */
    String value();

    /**
     * 枚举类
     */
    Class<? extends BasePropertiesEnum<?>> enumClass();

    /**
     * 需要匹配的具体枚举（大小写不敏感）
     */
    String[] includeAllValue() default {};

    String[] includeAnyValue() default {};
}
