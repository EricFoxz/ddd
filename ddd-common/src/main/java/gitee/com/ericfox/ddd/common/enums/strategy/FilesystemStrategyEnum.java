package gitee.com.ericfox.ddd.common.enums.strategy;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;

public enum FilesystemStrategyEnum implements BaseEnum<FilesystemStrategyEnum, String> {
    MIN_IO_STRATEGY("minIoStrategy", "");
    private final String code;
    private final String comment;

    FilesystemStrategyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public FilesystemStrategyEnum[] getEnums() {
        return values();
    }
}
