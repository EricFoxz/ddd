package gitee.com.ericfox.ddd.apis.events.aspect;

import gitee.com.ericfox.ddd.common.interfaces.apis.BaseDetailParam;
import gitee.com.ericfox.ddd.common.interfaces.apis.BasePageParam;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserContext;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Controller层上下文切面
 */
@Component
@Aspect
public class ControllerContextAspect {
    @Pointcut(value = "execution(public * gitee.com.ericfox.ddd.apis.controller..*.*(..))")
    public void apiMethod() {
    }

    @Around(value = "apiMethod()")
    @SneakyThrows
    public Object aroundApiMethod(ProceedingJoinPoint proceedingJoinPoint) {
        //获取入参
        Object[] args = proceedingJoinPoint.getArgs();

        //FIXME 设置上下文
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BaseContext.BaseDescription description = SysUserContext.Description.DEFAULT;
        BaseContext.BaseMoment moment = SysUserContext.Moment.DEFAULT;
        BaseContext.BaseRule rule = SysUserContext.Rule.WEB_USER;

        for (Object arg : args) {
            if (arg instanceof BaseEntity) {
                BaseEntity entity = (BaseEntity) arg;
                entity.set_description(description);
                entity.set_moment(moment);
                entity.set_rule(rule);
            } else if (arg instanceof BasePageParam) {
                BasePageParam pageParam = (BasePageParam) arg;
                pageParam.set_description(description);
                pageParam.set_moment(moment);
                pageParam.set_rule(rule);
            } else if (arg instanceof BaseDetailParam) {
                BaseDetailParam detailParam = (BaseDetailParam) arg;
                detailParam.set_description(description);
                detailParam.set_moment(moment);
                detailParam.set_rule(rule);
            }
        }

        //执行
        Object response = proceedingJoinPoint.proceed(args);

        //控制response返回结果
        if (response instanceof ResBuilder) {
            ResBuilder resBuilder = (ResBuilder) response;
            return resBuilder.build();
        }
        return response;
    }
}
