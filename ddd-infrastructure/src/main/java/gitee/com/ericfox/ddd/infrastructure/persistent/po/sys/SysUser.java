package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.OrmEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@OrmEnabledAnnotation(type = RepoTypeStrategyEnum.LUCENE_REPO_STRATEGY)
public class SysUser implements BasePo<SysUser> {
    private Long id;
    private String username;
}
