package gitee.com.ericfox.ddd.common.enums.db;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * MySql存储引擎枚举类 10.4.22-MariaDB
 */
@Getter
public enum MySqlTableEngineEnum implements BaseEnum<MySqlTableEngineEnum, String> {
    CSV("CSV", "Stores tables as CSV files"),
    MRG_MYISAM("MRG_MyISAM", "Collection of identical MyISAM tables"),
    MEMORY("MEMORY", "Hash based, stored in memory, useful for temporary tables"),
    ARIA("Aria", "Crash-safe tables with MyISAM heritage. Used for internal temporary tables and privilege tables"),
    MYISAM("MyISAM", "Non-transactional engine with good performance and small data footprint"),
    SEQUENCE("SEQUENCE", "Generated tables filled with sequential values"),
    INNODB("InnoDB", "Supports transactions, row-level locking, foreign keys and encryption for tables"),
    PERFORMANCE_SCHEMA("PERFORMANCE_SCHEMA", "Performance Schema");
    private final String code;
    private final String comment;

    MySqlTableEngineEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public MySqlTableEngineEnum[] getEnums() {
        return values();
    }
}
