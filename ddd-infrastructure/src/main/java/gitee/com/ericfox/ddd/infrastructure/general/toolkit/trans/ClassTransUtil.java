package gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans;

import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BaseDao;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ReUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import lombok.SneakyThrows;

import java.util.List;

@SuppressWarnings("unchecked")
public class ClassTransUtil {
    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>> Class<DAO> getDaoClassByPoClass(Class<PO> clazz, RepoTypeStrategyEnum repoTypeStrategyEnum) {
        String fullName = clazz.getName();
        String simpleName = clazz.getSimpleName();
        List<String> domainName = ReUtil.findAll("\\.([^.]+)\\." + simpleName, fullName, 1);
        return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".dao." + domainName.get(0) + "." + StrUtil.toUnderlineCase(repoTypeStrategyEnum.getCode()) + "." + simpleName + "Dao");
    }

    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>> Class<DAO> getDaoClassByPo(PO po, RepoTypeStrategyEnum repoTypeStrategyEnum) {
        return getDaoClassByPoClass((Class<PO>) po.getClass(), repoTypeStrategyEnum);
    }

    @SneakyThrows
    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> String getTableNameByPoClass(Class<PO> clazz) {
        return (String) clazz.getDeclaredClasses()[0].getField("table").get(null);
    }

    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> String getTableNameByPo(PO t) {
        return getTableNameByPoClass(t.getClass());
    }
}
