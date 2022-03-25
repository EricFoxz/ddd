package gitee.com.ericfox.ddd.application.framework.controller.r_socket;

import gitee.com.ericfox.ddd.application.framework.model.r_socket.RSocketMessageBean;
import gitee.com.ericfox.ddd.application.framework.service.r_socket.ApplicationFrameworkRSocketService;
import gitee.com.ericfox.ddd.application.framework.config.r_socket.RSocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;

@Slf4j
@Controller
@ConditionalOnBean(RSocketConfig.class)
public class ApplicationFrameworkRSocketController {
    @Resource
    private ApplicationFrameworkRSocketService applicationFrameworkRSocketService;

    @MessageMapping("rSocket.echo")
    public Mono<RSocketMessageBean> echo(Mono<RSocketMessageBean> messageBean) {
        return messageBean.doOnNext(msg -> applicationFrameworkRSocketService.echo(msg))
                .doOnNext(msg -> log.info("消息接收: {}", msg));
    }

    @MessageMapping("rSocket.delete")
    public Disposable delete(Mono<RSocketMessageBean> title) {
        return title.doOnNext(msg -> log.info("删除消息: {}", msg)).subscribe();
    }

    @MessageMapping("rSocket.list")
    public Flux<RSocketMessageBean> list() {
        return Flux.fromStream(applicationFrameworkRSocketService.list().stream());
    }

    @MessageMapping("rSocket.get")
    public Flux<RSocketMessageBean> get(Flux<String> title) {
        return title.doOnNext(t -> log.info("信息查询 {}", t))
                .map(String::toLowerCase)
                .map(applicationFrameworkRSocketService::get)
                .delayElements(Duration.ofSeconds(1));
    }
}
