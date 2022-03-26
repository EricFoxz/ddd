package gitee.com.ericfox.ddd.application.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Application层启动类
 */
@SpringBootApplication(
        scanBasePackages = {
                "gitee.com.ericfox.ddd.application.*.controller",
                "gitee.com.ericfox.ddd.application.*.service",
                "gitee.com.ericfox.ddd.application.*.config",
                "gitee.com.ericfox.ddd.domain.*.model",
                "gitee.com.ericfox.ddd.domain.*.service",
                "gitee.com.ericfox.ddd.infrastructure.general.config",
                "gitee.com.ericfox.ddd.infrastructure.service",
                "gitee.com.ericfox.ddd.starter.*.properties",
                "gitee.com.ericfox.ddd.starter.*.service",
                "gitee.com.ericfox.ddd.starter.*.config",
                "gitee.com.ericfox.ddd.common.config",
                "gitee.com.ericfox.ddd.common.service",
        },
        exclude = {HibernateJpaAutoConfiguration.class}
)
public class ApplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationApplication.class, args);
    }
}
