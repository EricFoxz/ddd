package gitee.com.ericfox.ddd.apis.events.aspect;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
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
    public void apiControllerMethod() {
    }

    @Around(value = "apiControllerMethod()")
    @SneakyThrows
    public Object aroundApiControllerMethod(ProceedingJoinPoint proceedingJoinPoint) {
        //获取入参
        Object[] args = proceedingJoinPoint.getArgs();

        //FIXME 设置上下文
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        BaseContext.BaseDescription description = SysUserContext.Description.DEFAULT;
//        BaseContext.BaseMoment moment = SysUserContext.Moment.DEFAULT;
//        BaseContext.BaseRule rule = SysUserContext.Rule.WEB_USER;

        for (Object arg : args) {
            if (arg instanceof BaseEntity) {
                BaseEntity entity = (BaseEntity) arg;
//                entity.set_description(description);
//                entity.set_moment(moment);
//                entity.set_rule(rule);
            }
        }

        //执行
        Object response = proceedingJoinPoint.proceed(args);

        //控制response返回结果
        /*if (response instanceof ResBuilder) {
            ResBuilder resBuilder = (ResBuilder) response;
            return resBuilder.build();
        }*/
        return response;
    }
}
