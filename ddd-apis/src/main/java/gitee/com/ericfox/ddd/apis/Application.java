package gitee.com.ericfox.ddd.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "gitee.com.ericfox.ddd.infrastructure.general.config",
                "gitee.com.ericfox.ddd.infrastructure.persistent.service",
                "gitee.com.ericfox.ddd.apis.controller",
                "gitee.com.ericfox.ddd.domain.sys.model",
        }
)

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
