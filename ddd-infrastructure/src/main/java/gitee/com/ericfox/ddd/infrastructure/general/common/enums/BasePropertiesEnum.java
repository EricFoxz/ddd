package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

import java.io.Serializable;

public interface BasePropertiesEnum<T extends BaseEnum<T, ?>> {
    T toBizEnum();
}
