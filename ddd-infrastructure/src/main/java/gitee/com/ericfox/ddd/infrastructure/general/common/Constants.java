package gitee.com.ericfox.ddd.infrastructure.general.common;

import cn.hutool.core.bean.copier.CopyOptions;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;

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
}
