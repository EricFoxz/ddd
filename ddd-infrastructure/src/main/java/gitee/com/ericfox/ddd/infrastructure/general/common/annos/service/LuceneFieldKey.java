package gitee.com.ericfox.ddd.infrastructure.general.common.annos.service;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuceneFieldKey {
    LuceneFieldTypeEnum type();

    boolean needSort() default false;
}
