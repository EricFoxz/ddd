package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * OkHttp配置列
 */
@Component
public class OkHttpConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
