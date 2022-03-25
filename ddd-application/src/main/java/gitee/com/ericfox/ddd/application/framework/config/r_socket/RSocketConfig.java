package gitee.com.ericfox.ddd.application.framework.config.r_socket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * RSocket配置类
 */
@Configuration
public class RSocketConfig {
    /**
     * 配置策略，编码解码
     */
    @Bean
    public RSocketStrategies rSocketStrategies() {
        return RSocketStrategies
                .builder()
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .build();
    }

    /**
     * RSocket连接策略
     */
    @Bean
    public Mono<RSocketRequester> rSocketMessageBeanMono(RSocketRequester.Builder builder) {
        return Mono.just(
                builder
                        .rsocketConnector(connector -> connector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                        .dataMimeType(MediaType.APPLICATION_CBOR)
                        .transport(TcpClientTransport.create(3002))
        );
    }
}
