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
 * token表
 */
@TableComment("token表")
@Setter
@Getter
@RepoEnabledAnnotation(type = RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY)
public class SysToken implements BasePo<SysToken> {

    public static final class STRUCTURE {
        public static String domainName = "sys";
        public static String table = "sys_token";
        public static String id = "id";
        public static String uuid = "uuid";
    }

    /**
     * 主键
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.BIGINT, length = 19, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "主键")
    private Long id;
    /**
     * 用户名
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 50, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "用户名")
    private String username;
    /**
     * 平台
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.VARCHAR, length = 20, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "平台")
    private String platform;
    /**
     * 令牌
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.CHAR, length = 32, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "令牌")
    private String token;
    /**
     * 刷新令牌
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.CHAR, length = 32, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "刷新令牌")
    private String refreshToken;
    /**
     * 过期时间
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.BIGINT, length = 19, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "过期时间")
    private Long expireDate;
    /**
     * 创建时间
     */
    @FieldSchema(dataType = MySqlDataTypeEnum.BIGINT, length = 19, scale = 0, isNullable = BooleanEnums.EnglishCode.YES, comment = "创建时间")
    private Long createDate;
}
