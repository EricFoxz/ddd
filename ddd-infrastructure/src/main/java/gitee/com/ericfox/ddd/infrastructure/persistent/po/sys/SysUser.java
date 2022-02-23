package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.FieldComment;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.FieldLength;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.TableComment;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户表
 */
@TableComment("系统用户表")
@Setter
@Getter
@RepoEnabledAnnotation(type = RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY)
public class SysUser implements BasePo<SysUser> {
    public static final class STRUCTURE {
        public static String domainName = "sys";
        public static String table = "sys_user";
        public static String id = "id";
    }

    @FieldLength(length = 19, scale = 0)
    @FieldComment("")
    private Long id;
    /**
     * 用户名
     */
    @FieldLength(length = 32, scale = 0)
    @FieldComment("用户名")
    private String username;
    @FieldLength(length = 10, scale = 2)
    @FieldComment("")
    private java.math.BigDecimal money;
    @FieldLength(length = 65535, scale = 0)
    @FieldComment("")
    private String userInfo;
}
