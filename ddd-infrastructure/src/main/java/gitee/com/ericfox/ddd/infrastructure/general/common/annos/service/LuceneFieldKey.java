package gitee.com.ericfox.ddd.infrastructure.general.common.annos.service;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;

import java.lang.annotation.*;

/**
 * Lucene实体类字段注解（影响索引效果）
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuceneFieldKey {
    LuceneFieldTypeEnum type();

    boolean needSort() default false;
}
