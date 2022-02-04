package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.LuceneRepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.MySqlRepoStrategy;
import lombok.SneakyThrows;

import java.util.List;

@SuppressWarnings("unchecked")
public class ClassUtil extends cn.hutool.core.util.ClassUtil {
    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>> Class<DAO> getDaoClassByPoClass(Class<PO> clazz, RepoStrategy strategy) {
        String fullName = clazz.getName();
        String simpleName = clazz.getSimpleName();
        List<String> domainName = ReUtil.findAll("\\.([^.]+)\\." + simpleName, fullName, 1);
        //TODO-适配更多持久化方式
        if (strategy instanceof LuceneRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".repository." + domainName.get(0) + ".lucene." + simpleName + "Dao");
        } else if (strategy instanceof MySqlRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".repository." + domainName.get(0) + ".j_final." + simpleName + "Dao");
        }
        return null;
    }

    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>> Class<DAO> getDaoClassByPo(PO po, RepoStrategy strategy) {
        return getDaoClassByPoClass((Class<PO>) po.getClass(), strategy);
    }

    @SneakyThrows
    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> String getTableNameByPoClass(Class<PO> clazz) {
        return (String) clazz.getDeclaredClasses()[0].getField("table").get(null);
    }

    public static <PO extends BasePo<PO>, DAO extends BaseDao<PO>, ENTITY extends BaseEntity<PO, ENTITY>> String getTableNameByPo(PO t) {
        return getTableNameByPoClass(t.getClass());
    }
}
