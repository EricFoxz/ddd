package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqServerService;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.proxy.MqBroadcastProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
public class MqController {
    @Resource
    private MqServerService mqServerService;


    @GetMapping("/send/{msg}")
    public ResponseEntity<?> send(@PathVariable String msg) {
        mqServerService.send(msg, new MqBroadcastProxy("default"));
        return ResBuilder.defValue.success().build();
    }
}
