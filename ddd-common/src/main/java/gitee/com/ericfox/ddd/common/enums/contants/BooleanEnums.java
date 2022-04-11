package gitee.com.ericfox.ddd.common.enums.contants;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 布尔值枚举类
 */
@Getter
public final class BooleanEnums {
    @Getter
    public enum EnglishCode implements BaseEnum<EnglishCode, String> {
        YES("YES", "true"),
        NO("NO", "false");

        private final String code;
        private final String comment;

        EnglishCode(String code, String comment) {
            this.code = code;
            this.comment = comment;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public EnglishCode[] getEnums() {
            return values();
        }
    }

    @Getter
    public enum ChineseCode implements BaseEnum<ChineseCode, String> {
        YES("是", "true"),
        NO("否", "false");

        private final String code;
        private final String comment;

        ChineseCode(String code, String comment) {
            this.code = code;
            this.comment = comment;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public ChineseCode[] getEnums() {
            return values();
        }
    }

    @Getter
    public enum NumberCode implements BaseEnum<NumberCode, Integer> {
        YES(1, "true"),
        NO(0, "false");

        private final Integer code;
        private final String comment;

        NumberCode(Integer code, String comment) {
            this.code = code;
            this.comment = comment;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public NumberCode[] getEnums() {
            return values();
        }
    }

    @Getter
    public enum CharCode implements BaseEnum<CharCode, String> {
        YES("Y", "true"),
        NO("N", "false");

        private final String code;
        private final String comment;

        CharCode(String code, String comment) {
            this.code = code;
            this.comment = comment;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public CharCode[] getEnums() {
            return values();
        }
    }
}
