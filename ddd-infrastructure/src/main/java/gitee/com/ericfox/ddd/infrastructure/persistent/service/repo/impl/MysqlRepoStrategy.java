package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import com.github.pagehelper.PageInfo;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Service("mysqlRepoStrategy")
public class MysqlRepoStrategy implements RepoStrategy {

    public <T extends BasePo<T>> T findById(T t) {
        Model model = getModel(t);
        Model result = model.findById(t.getId());
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return t;
    }

    @Override
    public <T extends BasePo<T>> boolean deleteById(T t) {
        Model model = getModel(t);
        return model.deleteById(t.getId());
    }

    @Override
    public <T extends BasePo<T>> T insert(T t) {
        Model model = getModel(t);
        Model result = model.put(model);
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return t;
    }

    @Override
    public <T extends BasePo<T>> boolean update(T t) {
        Model model = getModel(t);
        model = model.findById(t.getId());
        BeanUtil.copyProperties(t, model, false);
        return model.update();
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
        Model model = getModel(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from " + t.getTableName());
        Page<Model> paginate = model.paginate(pageNum, pageSize, sqlPara);
        List<T> result = CollUtil.newArrayList();
        if(paginate.getList() != null) {
            for (Model tmp : paginate.getList()) {
                T n = (T) t.getClass().newInstance();
                BeanUtil.copyProperties(tmp.toRecord().getColumns(), n, false);
                result.add(n);
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(paginate.getPageNumber());
        pageInfo.setPageSize(paginate.getPageSize());
        pageInfo.setTotal(paginate.getTotalRow());
        pageInfo.setList(result);
        return pageInfo;
    }

    @Override
    public <T extends BasePo<T>> List<T> queryList(T t, int limit) {
        Model model = getModel(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.addPara(t);
        List list = model.find(sqlPara.getSql() + " limit " + limit);
        List<?> result = BeanUtil.copyToList(list, t.getClass());
        return (List<T>) result;
    }

    @SneakyThrows
    private <T extends BasePo<T>> Model getModel(T t) {
        Method toParent = ClassUtil.getPublicMethod(t.getClass(), "toParent", null);
        if (toParent != null) {
            t = (T) toParent.invoke(t, null);
        }
        Class<T> tClass = (Class<T>) t.getClass();
        String name = tClass.getName();
        String className = tClass.getSimpleName();
        Class<? extends Model<?>> daoClass = ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", name) + ".repository.sys.mysql." + className + "Dao");
        Field dao = daoClass.getDeclaredField("dao");
        dao.setAccessible(true);
        return (Model) dao.get(null);
    }
}
