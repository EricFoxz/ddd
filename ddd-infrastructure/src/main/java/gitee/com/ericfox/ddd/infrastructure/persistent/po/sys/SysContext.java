package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.*;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
import lombok.Getter;
import lombok.Setter;

@TableComment("上下文表")
@TableIndexes({
        @TableIndex(name = "sys_context__table_name", column = "table_name"),
        @TableIndex(name = "sys_context__code", column = "code"),
        @TableIndex(name = "sys_context__type_enum", column = "type_enum"),
})
@TableKeys({
        @TableKey("id")
})
@Setter
@Getter
@RepoEnabledAnnotation(type = RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY)
public class SysContext implements BasePo<SysContext> {
    public static final class STRUCTURE {
        public static String domainName = "sys";
        public static String table = "sys_context";
        public static String id = "id";
        public static String uuid = "uuid";
    }

    /**
     * 主键
     */
    private Long id;
    private String tableName;
    private String code;

    private String typeEnum;
    private String responseBodyScript;
}
