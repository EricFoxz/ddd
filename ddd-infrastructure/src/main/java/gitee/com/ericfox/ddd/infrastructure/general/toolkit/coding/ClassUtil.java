package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.JFinalRepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.LuceneRepoStrategy;
import lombok.SneakyThrows;

import java.util.List;

@SuppressWarnings("unchecked")
public class ClassUtil extends cn.hutool.core.util.ClassUtil {
    public static <T extends BasePo<T>, U extends BaseDao<T>> Class<U> getDaoClassByPoClass(Class<T> clazz, RepoStrategy strategy) {
        String fullName = clazz.getName();
        String simpleName = clazz.getSimpleName();
        List<String> domainName = ReUtil.findAll("\\.([^.]+)\\." + simpleName, fullName, 1);
        //TODO-适配更多持久化方式
        if (strategy instanceof LuceneRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".repository." + domainName.get(0) + ".lucene." + simpleName + "Dao");
        } else if (strategy instanceof JFinalRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".repository." + domainName.get(0) + ".j_final." + simpleName + "Dao");
        }
        return null;
    }

    public static <T extends BasePo<T>, U extends BaseDao<T>> Class<U> getDaoClassByPo(T t, RepoStrategy strategy) {
        return getDaoClassByPoClass((Class<T>) t.getClass(), strategy);
    }

    @SneakyThrows
    public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> String getTableNameByPoClass(Class<T> clazz) {
        return (String) clazz.getDeclaredClasses()[0].getField("table").get(null);
    }

    public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> String getTableNameByPo(T t) {
        return getTableNameByPoClass(t.getClass());
    }
}
