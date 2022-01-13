package gitee.com.ericfox.ddd.infrastructure.persistent.service.socket;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Component
@Slf4j
public class RSocketRepository {
    private static final int BOUND = 100;

    public Flux<?> getAll(String stock) {
        return Flux.fromStream(Stream.generate(() -> getSocketData(stock))).log().delayElements(Duration.ofSeconds(1));
    }

    public Mono<?> getOne(String stock) {
        return Mono.just(getSocketData(stock));
    }

    public void add(Object data) {
        log.info("new Socket Data! {}", data);
    }

    private Object getSocketData(String stock) {
        return MapUtil.newHashMap();
    }
}
