package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.infrastructure.persistent.service.socket.RSocketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Slf4j
public class RSocketController {
    @Resource
    private RSocketRepository rSocketRepository;

    @MessageMapping("currentSocketData")
    public Mono<?> currentData(Map<String, Object> request) {
        return rSocketRepository.getOne((String) request.get("name"));
    }
}
