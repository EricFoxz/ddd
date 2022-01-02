package gitee.com.ericfox.ddd.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan("gitee.com.ericfox.ddd.infrastructure.general.config"),
        @ComponentScan("gitee.com.ericfox.ddd.apis.controller"),
})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
