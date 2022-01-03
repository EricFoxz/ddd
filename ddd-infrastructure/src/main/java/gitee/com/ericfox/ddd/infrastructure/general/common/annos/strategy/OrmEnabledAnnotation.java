package gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrmEnabledAnnotation {
    /**
     * 数据主键
     */
    String value() default "id";

    RepoTypeStrategyEnum type() default RepoTypeStrategyEnum.J_FINAL_REPO_STRATEGY;
}
