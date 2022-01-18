package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqService;
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
    private MqService mqService;

    @GetMapping("/send/{msg}")
    public ResponseEntity<?> send(@PathVariable String msg) {
        mqService.send(msg);
        return ResBuilder.defValue.success().build();
    }
}
