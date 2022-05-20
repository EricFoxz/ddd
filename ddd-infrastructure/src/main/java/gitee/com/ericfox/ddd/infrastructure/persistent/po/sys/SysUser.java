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

    /**
     * 主键
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.BIGINT, length = 19, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "主键")
    private Long id;
    /**
     * uuid可以存储第三方主键
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 32, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "uuid可以存储第三方主键")
    private String uuid;
    /**
     * 用户名
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 50, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "用户名")
    private String username;
    /**
     * 昵称
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 20, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "昵称")
    private String nickname;
    /**
     * 密码
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 32, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "密码")
    private String password;
    /**
     * 平台
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 255, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "平台")
    private String platform;
    /**
     * 用户信息
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.TEXT, length = 65535, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "用户信息")
    private String userinfo;
    /**
     * 状态
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.CHAR, length = 1, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "状态")
    private String status;
    @FieldSchema(dataType = MySqlDataTypeEnum.DATETIME, length = 0, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "")
    private java.sql.Timestamp createDate;
}
