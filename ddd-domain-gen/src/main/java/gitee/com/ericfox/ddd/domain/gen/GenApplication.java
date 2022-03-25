package gitee.com.ericfox.ddd.domain.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = {
                "gitee.com.ericfox.ddd.infrastructure.general.config",
                "gitee.com.ericfox.ddd.infrastructure.service",
                "gitee.com.ericfox.ddd.domain.gen.common",
                "gitee.com.ericfox.ddd.domain.gen.service",
        },
        exclude = {HibernateJpaAutoConfiguration.class}
)
public class GenApplication {
    public static void main(String[] args) {
        SpringApplication.run(GenApplication.class, args);
    }
}
