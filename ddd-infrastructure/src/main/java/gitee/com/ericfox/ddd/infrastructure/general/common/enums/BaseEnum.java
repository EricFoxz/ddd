package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

import java.io.Serializable;

public interface BaseEnum<T extends BaseEnum<T, U>, U extends Serializable> {
    String getName();
    U getCode();

    String getDescription();

    T[] getEnums();

    default T getEnumByName(U code) {
        T[] enums = getEnums();
        for (T anEnum : enums) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
    default T getEnumByCode(U code) {
        T[] enums = getEnums();
        for (T anEnum : enums) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

    default T getEnumByDescription(U description) {
        T[] enums = getEnums();
        for (T anEnum : enums) {
            if (anEnum.getDescription().equals(description)) {
                return anEnum;
            }
        }
        return null;
    }
}
