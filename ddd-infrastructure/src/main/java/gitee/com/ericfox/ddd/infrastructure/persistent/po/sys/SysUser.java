package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@RepoEnabledAnnotation(type = RepoTypeStrategyEnum.LUCENE_REPO_STRATEGY)
public class SysUser implements BasePo<SysUser> {
    public static final class STRUCTURE {
        public static String table = "sys_user";
        public static String id = "id";
    }

    private Long id;
    private String username;
}
