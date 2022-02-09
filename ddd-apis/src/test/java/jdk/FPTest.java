package jdk;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.MySqlRepoStrategy;

public class FPTest {
    public static void main(String[] args) {
        Class<BaseDao<SysUser>> daoClassByPo = ClassUtil.getDaoClassByPo(new SysUser(), RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
        System.out.println(daoClassByPo);
    }
}
