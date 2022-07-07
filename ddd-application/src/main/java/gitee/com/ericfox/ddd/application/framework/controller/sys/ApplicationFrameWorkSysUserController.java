package gitee.com.ericfox.ddd.application.framework.controller.sys;

import gitee.com.ericfox.ddd.infrastructure.service.socket.RSocketRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Controller
public class ApplicationFrameWorkSysUserController {
    @Resource
    RSocketRepository rSocketRepository;

    @MessageMapping("application.framework.controller.sys.ApplicationFrameWorkSysUserController.pong")
    public Mono<String> pong(String pong) {
        return Mono.just("ECHO >> " + pong);
    }

    @MessageMapping("application.framework.controller.sys.ApplicationFrameWorkSysUserController.echo")
    public Flux<String> echo(String echo) {
        return Flux.fromStream(echo.codePoints().mapToObj(msg -> {
            System.out.println("ECHO >> " + msg);
            return null;
        }));
    }
}
