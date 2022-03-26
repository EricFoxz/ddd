package gitee.com.ericfox.ddd.application.framework.test;

import gitee.com.ericfox.ddd.application.framework.model.r_socket.RSocketMessageBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * RSocket测试类
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class RSocketTest {
    @Resource
    private Mono<RSocketRequester> requesterMono;

    @Test
    public void testEcho() {
        requesterMono
                .map(rSocketRequester -> {
                    RSocketMessageBean messageBean = new RSocketMessageBean();
                    messageBean.setTitle("标题");
                    messageBean.setContent("内容");
                    return rSocketRequester.route("rSocket.echo")
                            .data(messageBean);
                })
                .flatMap(retrieveSpec -> retrieveSpec.retrieveMono(RSocketMessageBean.class))
                .doOnNext(object -> log.info(object.toString())).block();
    }
}
