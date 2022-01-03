package gitee.com.ericfox.ddd.infrastructure.general.common.annos;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.RepoTypeStrategyEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrmEnabledAnnotation {
    /**
     * 数据主键
     */
    String value() default "id";

    RepoTypeStrategyEnum type() default RepoTypeStrategyEnum.J_FINAL;
}
