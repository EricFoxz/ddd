package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;

public interface BasePropertiesEnum<T extends BaseEnum<T, ?>> {
    String getName();

    T toBizEnum();

    default boolean equals(String name) {
        return StrUtil.equalsIgnoreCase(this.getName(), name);
    }
}
