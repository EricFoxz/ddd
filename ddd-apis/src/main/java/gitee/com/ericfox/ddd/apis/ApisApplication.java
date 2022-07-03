package gitee.com.ericfox.ddd.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Api应用启动类
 */
@SpringBootApplication(
        scanBasePackages = {
                "gitee.com.ericfox.ddd.apis.controller",
                "gitee.com.ericfox.ddd.apis.events",
                "gitee.com.ericfox.ddd.application.*.controller",
                "gitee.com.ericfox.ddd.application.*.service",
                "gitee.com.ericfox.ddd.application.*.config",
                "gitee.com.ericfox.ddd.application.*.events",
                "gitee.com.ericfox.ddd.domain.*.model",
                "gitee.com.ericfox.ddd.domain.*.service",
                "gitee.com.ericfox.ddd.domain.*.config",
                "gitee.com.ericfox.ddd.domain.*.events",
                "gitee.com.ericfox.ddd.infrastructure.general.config",
                "gitee.com.ericfox.ddd.infrastructure.service",
                "gitee.com.ericfox.ddd.starter.*.properties",
                "gitee.com.ericfox.ddd.starter.*.service",
                "gitee.com.ericfox.ddd.starter.*.config",
                "gitee.com.ericfox.ddd.common.config",
                "gitee.com.ericfox.ddd.common.service",
        },
        exclude = {
                HibernateJpaAutoConfiguration.class
        }
)
public class ApisApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApisApplication.class, args);
    }
}
