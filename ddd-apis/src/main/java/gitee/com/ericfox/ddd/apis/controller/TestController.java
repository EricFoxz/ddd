package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResponseEntityBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apis")
public class TestController {
    @GetMapping("/index")
    @ResponseBody
    public ResponseEntity<?> index() {
        return ResponseEntityBuilder.blobData("成功").status(200).build();
    }
}
