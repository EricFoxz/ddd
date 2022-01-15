package gitee.com.ericfox.ddd.apis.controller;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cache")
public class CacheController {
    @Resource
    private CacheService cacheService;

    @GetMapping("/get/{key}")
    public ResponseEntity<?> get(@PathVariable String key) {
        Object value = cacheService.get(key);
        return ResBuilder.defValue.success().put("data", value).build();
    }

    @GetMapping("/put/{key}/{value}")
    public ResponseEntity<?> put(@PathVariable String key, @PathVariable String value) {
        cacheService.put(key, value);
        return ResBuilder.defValue.success().build();
    }
}
