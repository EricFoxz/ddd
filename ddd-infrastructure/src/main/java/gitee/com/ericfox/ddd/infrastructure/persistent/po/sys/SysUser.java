package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.common.enums.contants.BooleanEnums;
import gitee.com.ericfox.ddd.common.enums.db.MySqlDataTypeEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.*;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户表
 */
@TableComment("系统用户表")
@TableIndexes(
        @TableIndex(name = "sys_user__username", column = "username")
)
@TableKeys(
        @TableKey("id")
)
@Setter
@Getter
@RepoEnabledAnnotation(type = RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY)
public class SysUser implements BasePo<SysUser> {
    public static final class STRUCTURE {
        public static String domainName = "sys";
        public static String table = "sys_user";
        public static String id = "id";
        public static String uuid = "uuid";
    }

    @FieldSchema(dataType = MySqlDataTypeEnum.BIGINT, length = 19, scale = 0)
    private Long id;
    /**
     * 用户名
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 32, isNullable = BooleanEnums.EnglishCode.NO, comment = "用户名")
    private String username;
    /**
     * 金额
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.DECIMAL, length = 10, scale = 2, isNullable = BooleanEnums.EnglishCode.NO, comment = "金额")
    private java.math.BigDecimal money;
    @FieldSchema(dataType = MySqlDataTypeEnum.TEXT, length = 65535)
    private String userInfo;
}
