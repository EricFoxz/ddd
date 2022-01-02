package gitee.com.ericfox.ddd.apis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/api")
@ResponseBody
public class TestController {
    @GetMapping("/index")
    public String index () {
        return "{'msg': '成功'}";
    }
}
