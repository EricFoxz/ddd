package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

import java.io.Serializable;

/**
 * 基础枚举类接口
 *
 * @param <T> 子类
 * @param <U> 枚举的唯一码 code的类型
 */
public interface BaseEnum<T extends BaseEnum<T, U>, U extends Serializable> {
    String getName();

    /**
     * 唯一code
     */
    U getCode();

    /**
     * 描述
     */
    String getDescription();

    /**
     * 该类的所有枚举array
     */
    T[] getEnums();

    /**
     * 通过name获取枚举
     */
    default T getEnumByName(U code) {
        T[] enums = getEnums();
        for (T anEnum : enums) {
            if (anEnum.getName().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 通过唯一码code获取枚举
     */
    default T getEnumByCode(U code) {
        T[] enums = getEnums();
        for (T anEnum : enums) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 通过描述获取枚举
     */
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
