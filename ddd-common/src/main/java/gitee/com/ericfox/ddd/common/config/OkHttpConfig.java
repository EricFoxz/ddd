package gitee.com.ericfox.ddd.common.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkHttp配置列
 */
@Configuration
public class OkHttpConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
