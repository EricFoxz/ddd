package gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheEnabledAnnotation {
}