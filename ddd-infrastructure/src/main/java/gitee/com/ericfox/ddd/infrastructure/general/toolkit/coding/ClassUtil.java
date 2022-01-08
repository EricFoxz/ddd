package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.JFinalRepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.LuceneRepoStrategy;

public class ClassUtil extends cn.hutool.core.util.ClassUtil {
    public static <T extends BasePo<T>, U extends BaseDao<T>> Class<U> getDaoClassByPoClass(Class<T> clazz, RepoStrategy strategy) {
        String name = clazz.getName();
        String className = clazz.getSimpleName();
        if (strategy instanceof LuceneRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", name) + ".repository.sys.lucene." + className + "Dao");
        } else if (strategy instanceof JFinalRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", name) + ".repository.sys.jfinal." + className + "Dao");
        }
        return null;
    }

    public static <T extends BasePo<T>, U extends BaseDao<T>> Class<U> getDaoClassByPo(T t, RepoStrategy strategy) {
        return getDaoClassByPoClass((Class<T>) t.getClass(), strategy);
    }
}
