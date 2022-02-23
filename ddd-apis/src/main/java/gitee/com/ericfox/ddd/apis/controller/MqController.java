package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.common.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqProxy;
import gitee.com.ericfox.ddd.starter.mq.service.MqServerService;
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
        mqServerService.send(msg, MqProxy.getServerInstance("default"));
        return ResBuilder.defValue.success().build();
    }
}
