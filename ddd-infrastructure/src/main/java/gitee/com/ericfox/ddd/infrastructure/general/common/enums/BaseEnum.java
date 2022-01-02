package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

public interface BaseEnum<T> {
    T getCode();
    String getDescription();
}
