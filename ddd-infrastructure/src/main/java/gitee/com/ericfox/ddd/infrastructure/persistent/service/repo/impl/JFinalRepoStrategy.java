package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import com.github.pagehelper.PageInfo;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.JFinalBaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service("jFinalRepoStrategy")
public class JFinalRepoStrategy implements RepoStrategy {

    public <T extends BasePo<T>, U extends BaseDao<T>> T findById(T t) {
        JFinalBaseDao dao = getDao(t);
        Model result = dao.findById(t.getId());
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return t;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean deleteById(T t) {
        JFinalBaseDao dao = getDao(t);
        return dao.deleteById(t.getId());
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> T insert(T t) {
        JFinalBaseDao dao = getDao(t);
        Model result = dao.put(dao);
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return t;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(List<T> t) {
        if (CollUtil.isEmpty(t)) {
            return true;
        }
        JFinalBaseDao dao = getDao(t.get(0));
        return dao.multiInsert(t, t.size());
    }

    @SafeVarargs
    @Override
    public final <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(T... t) {
        return multiInsert(CollUtil.newArrayList(t));
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean update(T t) {
        JFinalBaseDao dao = getDao(t);
        t = (T) dao.findById(t.getId());
        BeanUtil.copyProperties(t, dao, false);
        return dao.update();
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
        Map<String, Object> param = BeanUtil.beanToMap(t);
        Class<JFinalBaseDao> daoClassByPo = ClassUtil.getDaoClassByPo(t, this);
        JFinalBaseDao dao = getDao(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from " + StrUtil.toUnderlineCase(t.getClass().getSimpleName()));
        if (CollUtil.isNotEmpty(param)) {
            sqlPara.setSql(sqlPara.getSql() + " where 1 = 1 ");
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    String key = entry.getKey();
                    sqlPara.setSql(sqlPara.getSql() + " and " + StrUtil.toUnderlineCase(key) + " like ? ");
                    sqlPara.addPara(value);
                }
            }
        }
        Page<JFinalBaseDao> paginate = dao.paginate(pageNum, pageSize, sqlPara);
        List<T> result = CollUtil.newArrayList();
        if (paginate.getList() != null) {
            for (Model<JFinalBaseDao> tmp : paginate.getList()) {
                T n = ReflectUtil.newInstance((Class<T>) t.getClass());
                BeanUtil.copyProperties(tmp.toRecord().getColumns(), n, false);
                result.add(n);
            }
        }
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(paginate.getPageNumber());
        pageInfo.setPageSize(paginate.getPageSize());
        pageInfo.setTotal(paginate.getTotalRow());
        pageInfo.setList(result);
        return pageInfo;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> List<T> queryList(T t) {
        return RepoStrategy.super.queryList(t);
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>> List<T> queryList(T t, int limit) {
        Map<String, Object> param = BeanUtil.beanToMap(t);
        JFinalBaseDao dao = getDao(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from " + StrUtil.toUnderlineCase(t.getClass().getSimpleName()) + " limit " + limit);
        List<Model<?>> list = dao.find(sqlPara.getSql());
        List<T> result = CollUtil.newArrayList();
        if (list != null) {
            for (Model<?> tmp : list) {
                T n = ReflectUtil.newInstance((Class<T>) t.getClass());
                BeanUtil.copyProperties(tmp.toRecord().getColumns(), n, false);
                result.add(n);
            }
        }
        return result;
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends JFinalBaseDao<T, U>> JFinalBaseDao<T, U> getDao(T t) {
        Method toPoMethod = ClassUtil.getPublicMethod(t.getClass(), JFinalBaseDao.TO_PO_METHOD_NAME, (Class<?>) null);
        if (toPoMethod != null) {
            t = ReflectUtil.invoke(t, toPoMethod, (Object) null);
        }
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        Method daoNameMethod = ReflectUtil.getMethodByName(daoClass, JFinalBaseDao.DAO_NAME_METHOD_NAME);
        String daoName = (String) daoNameMethod.invoke(null, (Object) null);
        return (JFinalBaseDao<T, U>) ReflectUtil.getStaticFieldValue(ReflectUtil.getField(daoClass, daoName));
    }
}
