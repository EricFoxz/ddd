package gitee.com.ericfox.ddd.common.config;

import kotlinx.coroutines.scheduling.CoroutineScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoroutineConfig {
    @Bean
    public CoroutineScheduler coroutineScheduler() {
        return new CoroutineScheduler(50, 10000, 1000L * 600, "");
    }
}
