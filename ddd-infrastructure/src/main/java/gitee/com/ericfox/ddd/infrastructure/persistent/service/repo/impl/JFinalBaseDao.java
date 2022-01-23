package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * JFinal抽象Dao
 *
 * @param <T> Po实体类
 * @param <U> Dao实体类
 */
@Slf4j
public abstract class JFinalBaseDao<T extends BasePo<T>, U extends JFinalBaseDao<T, U>> extends Model<U> implements BaseDao<T> {
    public static final String DAO_NAME_METHOD_NAME = "daoFieldName";

    protected static String daoFieldName() {
        return "dao";
    }

    public boolean multiInsert(List<U> t, int batchSize) {
        try {
            int[] ints = Db.batchSave(t, batchSize);
            return ArrayUtil.isNotEmpty(ints);
        } catch (Exception e) {
            log.error("jFinal批量入库失败");
        }
        return false;
    }
}
