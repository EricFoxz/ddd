package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.FieldComment;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.FieldLength;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.TableComment;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
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

    /**
     * 
     */
    @FieldLength(20)
    @FieldComment("")
    private Long id;
    /**
     * 用户名
     */
    @FieldLength(32)
    @FieldComment("用户名")
    private String username;
    /**
     * 
     */
    @FieldLength(10)
    @FieldComment("")
    private java.math.BigDecimal money;
    /**
     * 
     */
    @FieldLength(65535)
    @FieldComment("")
    private String userInfo;
}
