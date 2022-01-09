package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

public interface BasePropertiesEnum<T extends BaseEnum<T, ?>> {
    T toBizEnum();
}
