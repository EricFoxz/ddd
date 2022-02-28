package gitee.com.ericfox.ddd.infrastructure.general.common;

import cn.hutool.core.bean.copier.CopyOptions;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.api.ResBuilder;
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

    String SERVICE_FUNCTION_CACHE_KEY_GENERATOR = "serviceFunctionCacheKeyGenerator";
    String SERVICE_CACHE_KEY_GENERATOR = "serviceCacheKeyGenerator";

    String PROJECT_ROOT_PATH = System.getProperty("user.dir");

    Map<HttpStatus, ResponseEntity<?>> RESPONSE_ENTITY_MAP = MapUtil.newHashMap();

    static ResponseEntity<?> getResponseEntity(HttpStatus httpStatus) {
        if (RESPONSE_ENTITY_MAP.containsKey(httpStatus)) {
            return RESPONSE_ENTITY_MAP.get(httpStatus);
        } else {
            ResponseEntity<?> responseEntity = ResBuilder.hashMapData()
                    .put("msg", httpStatus.getReasonPhrase())
                    .setStatus(httpStatus)
                    .build();
            RESPONSE_ENTITY_MAP.put(httpStatus, responseEntity);
            return responseEntity;
        }
    }

}
