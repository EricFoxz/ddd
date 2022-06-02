package gitee.com.ericfox.ddd.common.interfaces.infrastructure;

import cn.hutool.core.bean.copier.CopyOptions;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface Constants {
    CopyOptions IGNORE_NULL_VALUE_COPY_OPTIONS = CopyOptions.create().ignoreNullValue();
    CopyOptions IGNORE_CASE_VALUE_COPY_OPTIONS = CopyOptions.create().ignoreCase();
    CopyOptions CAMEL_CASE_KEY_COPY_OPTIONS = CopyOptions.create().setFieldNameEditor(fieldName -> {
        if (StrUtil.isNotBlank(fieldName)) {
            return StrUtil.toCamelCase(fieldName);
        }
        return null;
    });

    Long TOKEN_EXPIRE_MILLIS_TIME = 1000L * 60 * 30;

    String SERVICE_FUNCTION_CACHE_KEY_GENERATOR = "serviceFunctionCacheKeyGenerator";
    String SERVICE_CACHE_KEY_GENERATOR = "serviceCacheKeyGenerator";

    String PROJECT_ROOT_PATH = System.getProperty("user.dir");

    Map<HttpStatus, ResponseEntity<?>> RESPONSE_ENTITY_MAP = MapUtil.newHashMap();

    static ResponseEntity<?> getResponseEntity(HttpStatus httpStatus) {
        if (RESPONSE_ENTITY_MAP.containsKey(httpStatus)) {
            return RESPONSE_ENTITY_MAP.get(httpStatus);
        } else {
            Map map = MapUtil.newHashMap();
            map.put("message", httpStatus.getReasonPhrase());
            ResponseEntity<?> responseEntity = new ResponseEntity<>(map, httpStatus);
            RESPONSE_ENTITY_MAP.put(httpStatus, responseEntity);
            return responseEntity;
        }
    }

}
