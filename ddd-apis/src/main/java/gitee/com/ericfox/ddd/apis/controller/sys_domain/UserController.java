package gitee.com.ericfox.ddd.apis.controller.sys_domain;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResponseEntityBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys/user")
@ResponseBody
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        return ResponseEntityBuilder.defValue.success().build();
    }

    @GetMapping("/page")
    public ResponseEntity<?> page() {
        return ResponseEntityBuilder.defValue.success().put("id", 1).build();
    }

    @PutMapping("/create")
    public ResponseEntity<?> create() {
        return ResponseEntityBuilder.defValue.created().build();
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> edit() {
        return ResponseEntityBuilder.defValue.success().put("id", 1).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        return ResponseEntityBuilder.defValue.success().put("id", 1).build();
    }
}
