package gitee.com.ericfox.ddd.infrastructure.general.config.customer;

import gitee.com.ericfox.ddd.infrastructure.general.config.CustomProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
public class CustomConfig {
    @Resource
    private Environment environment;

    /*@Bean
    public CustomProperties customProperties() {
        String[] activeProfiles = environment.getActiveProfiles();
        if(activeProfiles.length > 0) {

        }
        CustomProperties customProperties = environment.getProperty("custom", CustomProperties.class);
        return customProperties;
    }*/
}
