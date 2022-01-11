package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.JFinalRepoStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.LuceneRepoStrategy;

@SuppressWarnings("unchecked")
public class ClassUtil extends cn.hutool.core.util.ClassUtil {
    public static <T extends BasePo<T>, U extends BaseDao<T>> Class<U> getDaoClassByPoClass(Class<T> clazz, RepoStrategy strategy) {
        String fullName = clazz.getName();
        String simpleName = clazz.getSimpleName();
        //TODO-待实现 区分各个domain的包
        if (strategy instanceof LuceneRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".repository.sys.lucene." + simpleName + "Dao");
        } else if (strategy instanceof JFinalRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", fullName) + ".repository.sys.jfinal." + simpleName + "Dao");
        }
        return null;
    }

    public static <T extends BasePo<T>, U extends BaseDao<T>> Class<U> getDaoClassByPo(T t, RepoStrategy strategy) {
        return getDaoClassByPoClass((Class<T>) t.getClass(), strategy);
    }

    public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Class<U> getDaoClassByEntityClass(Class<V> clazz, RepoStrategy strategy) {
        String fullName = clazz.getName();
        String simpleName = clazz.getSimpleName();
        //TODO-待实现 区分各个domain的包
        if (strategy instanceof LuceneRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.domain\\..*", fullName) + ".infrastructure.persistent.repository.sys.lucene." + simpleName + "Dao");
        } else if (strategy instanceof JFinalRepoStrategy) {
            return ClassUtil.loadClass(ReUtil.delLast("\\.domain\\..*", fullName) + ".infrastructure.persistent.repository.sys.jfinal." + simpleName + "Dao");
        }
        return null;
    }

    public static<T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Class<U> getDaoClassByEntity(V v, RepoStrategy strategy) {
        return getDaoClassByEntityClass((Class<V>) v.getClass(), strategy);
    }
}
