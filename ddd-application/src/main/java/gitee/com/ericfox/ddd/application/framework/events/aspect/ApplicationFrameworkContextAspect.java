package gitee.com.ericfox.ddd.application.framework.events.aspect;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Application层上下文切面
 */
@Component
@Aspect
public class ApplicationFrameworkContextAspect {
    /**
     * 领域层Service方法
     */
    @Pointcut("execution(public * gitee.com.ericfox.ddd.domain.*.model..*Service.*(..)) " +
            "|| execution(public * gitee.com.ericfox.ddd.domain.*.model..*ServiceBase.*(..))")
    public void domainServiceMethod() {
    }

    @Around("domainServiceMethod()")
    @SneakyThrows
    public Object aroundDomainServiceMethod(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.proceed();
    }
}
