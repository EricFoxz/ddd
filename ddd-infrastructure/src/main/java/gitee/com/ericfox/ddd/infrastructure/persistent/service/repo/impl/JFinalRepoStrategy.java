package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import cn.hutool.core.bean.copier.CopyOptions;
import com.github.pagehelper.PageInfo;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans.SQL;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service("jFinalRepoStrategy")
@SuppressWarnings("unchecked")
public class JFinalRepoStrategy implements RepoStrategy {
    private static final CopyOptions updateCopyOptions = CopyOptions.create().ignoreCase().ignoreNullValue();

    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V findById(V v) {
        T t = v.toPo();
        JFinalBaseDao dao = getDao(t);
        Model<?> result = dao.findById(BeanUtil.getProperty(t, T.STRUCTURE.id));
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return v.fromPo(t);
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean deleteById(V v) {
        T t = v.toPo();
        JFinalBaseDao dao = getDao(t);
        return dao.deleteById(BeanUtil.getProperty(t, T.STRUCTURE.id));
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(List<V> v) {
        if (CollUtil.isEmpty(v)) {
            return true;
        }
        List<Serializable> idList = CollUtil.newArrayList();
        JFinalBaseDao dao = getDao(v.get(0).toPo());
        for (V tmp : v) {
            T t = tmp.toPo();
            Serializable id = BeanUtil.getProperty(t, T.STRUCTURE.id);
            idList.add(id);
        }
        dao.deleteByIds(idList);
        return false;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(V... v) {
        if (ArrayUtil.isEmpty(v)) {
            return true;
        }
        return multiDeleteById(CollUtil.newArrayList(v));
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V insert(V v) {
        T t = v.toPo();
        JFinalBaseDao dao = getDao(t);
        Model<?> result = dao.put(dao);
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return v;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(List<V> v) {
        if (CollUtil.isEmpty(v)) {
            return true;
        }
        JFinalBaseDao dao = getDao(v.get(0).toPo());
        return dao.multiInsert(v, v.size());
    }

    @SafeVarargs
    @Override
    public final <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(V... v) {
        return multiInsert(CollUtil.newArrayList(v));
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean updateById(V v) {
        T t = v.toPo();
        JFinalBaseDao dao = getDao(t);
        t = (T) dao.findById(BeanUtil.getProperty(t, T.STRUCTURE.id));
        BeanUtil.copyProperties(t, dao, updateCopyOptions);
        return dao.update();
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> PageInfo<V> queryPage(V v, int pageNum, int pageSize) {
        T t = v.toPo();
        Class<JFinalBaseDao> daoClass = ClassUtil.getDaoClassByPo(t, this);
        JFinalBaseDao dao = getDao(t);

        SQL whereSql = EasyQuery.parseWhereCondition(daoClass, v, true);
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("SELECT " + CollUtil.join(t.fields(), ",") + " FROM " + T.STRUCTURE.table + whereSql.toString());
        for (Object value : whereSql.getParamList()) {
            sqlPara.addPara(value);
        }
        Page<JFinalBaseDao> paginate = dao.paginate(pageNum, pageSize, sqlPara);
        List<V> result = CollUtil.newArrayList();
        if (paginate.getList() != null) {
            for (Model<JFinalBaseDao> tmp : paginate.getList()) {
                T po = ReflectUtil.newInstance((Class<T>) t.getClass());
                BeanUtil.copyProperties(tmp.toRecord().getColumns(), po, false);
                V vInstance = (V) ReflectUtil.newInstance(v.getClass());
                result.add(vInstance.fromPo(po));
            }
        }
        PageInfo<V> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(paginate.getPageNumber());
        pageInfo.setPageSize(paginate.getPageSize());
        pageInfo.setTotal(paginate.getTotalRow());
        pageInfo.setList(result);
        return pageInfo;
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> List<V> queryList(V v, int limit) {
        T t = v.toPo();
        Map<String, Object> param = BeanUtil.beanToMap(t);
        JFinalBaseDao dao = getDao(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from " + StrUtil.toUnderlineCase(t.getClass().getSimpleName()) + " limit " + limit);
        List<Model<?>> list = dao.find(sqlPara.getSql());
        List<V> result = CollUtil.newArrayList();
        if (list != null) {
            for (Model<?> tmp : list) {
                T po = ReflectUtil.newInstance((Class<T>) t.getClass());
                BeanUtil.copyProperties(tmp.toRecord().getColumns(), po, false);
                BeanUtil.copyProperties(tmp.toRecord().getColumns(), po, false);
                V vInstance = (V) ReflectUtil.newInstance(v.getClass());
                result.add(vInstance.fromPo(po));
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
        Method daoNameMethod = ReflectUtil.getMethod(daoClass, JFinalBaseDao.DAO_NAME_METHOD_NAME, (Class<?>) null);
        String daoName = (String) daoNameMethod.invoke(null, (Object) null);
        return (JFinalBaseDao<T, U>) ReflectUtil.getStaticFieldValue(ReflectUtil.getField(daoClass, daoName));
    }


    private static class EasyQuery {
        public static <T extends BasePo<T>, U extends JFinalBaseDao<T, U>, V extends BaseEntity<T, V>> SQL parseWhereCondition(Class<U> daoClass, V v, boolean matchAllIfEmpty) {
            Map<String, Object> conditionMap = v.get_condition().getConditionMap();
            SQL sql = SQL.getInstance().where();
            if (CollUtil.isEmpty(conditionMap) && matchAllIfEmpty) {
                sql.matchNothing();
            } else {
                sql.matchAll();
            }
            return parseWhereCondition(daoClass, v, conditionMap, sql);
        }

        private static <T extends BasePo<T>, U extends JFinalBaseDao<T, U>, V extends BaseEntity<T, V>> SQL parseWhereCondition(Class<U> daoClass, V v, Map<String, Object> conditionMap, SQL sql) {
            for (String key : conditionMap.keySet()) {
                String fieldName = BaseCondition.getFieldByConditionKey(key);
                String type = BaseCondition.getTypeByConditionKey(key);
                Object value = conditionMap.get(key);
                if (StrUtil.equals(type, BaseCondition.MATCH_ALL)) {
                    sql.and().matchAll();
                } else if (StrUtil.equals(type, BaseCondition.MATCH_NOTHING)) {
                    sql.and().matchNothing();
                } else if (StrUtil.equals(type, BaseCondition.OR)) {
                    sql.or(parseWhereCondition(daoClass, v, ((BaseCondition<?>) conditionMap.get(key)).getConditionMap(), sql));
                } else if (StrUtil.equals(type, BaseCondition.AND)) {
                    sql.and(parseWhereCondition(daoClass, v, ((BaseCondition<?>) conditionMap.get(key)).getConditionMap(), sql));
                } else if (StrUtil.equals(type, BaseCondition.EQUALS)) {
                    sql.and().equal(fieldName, value);
                } else if (StrUtil.equals(type, BaseCondition.NOT_EQUALS)) {
                    sql.and().notEqual();
                } else if (StrUtil.equals(type, BaseCondition.IS_NULL)) {
                    sql.and().isNull(fieldName);
                } else if (StrUtil.equals(type, BaseCondition.IS_NOT_NULL)) {
                    sql.and().isNotNull(fieldName);
                } else if (StrUtil.equals(type, BaseCondition.REGEX)) {
                    sql.and().regexp(((Pattern) value).pattern());
                } else if (StrUtil.equals(type, BaseCondition.GREAT_THAN)) {
                    sql.and().greatThan(fieldName, value);
                } else if (StrUtil.equals(type, BaseCondition.GREAT_THAN_OR_EQUALS)) {
                    sql.and().greatThanEqual(fieldName, value);
                } else if (StrUtil.equals(type, BaseCondition.LESS_THAN)) {
                    sql.and().lessThan(fieldName, value);
                } else if (StrUtil.equals(type, BaseCondition.LESS_THAN_OR_EQUALS)) {
                    sql.and().lessThanEqual(fieldName, value);
                } else if (StrUtil.equals(type, BaseCondition.BETWEEN)) {
                    List<?> list = (List<?>) value;
                    Object v1 = list.get(0);
                    Object v2 = list.get(1);
                    sql.and().between(fieldName, v1, v2);
                } else if (StrUtil.equals(type, BaseCondition.LIKE)) {
                    sql.and().likePrefix(fieldName, (String) value);
                } else if (StrUtil.equals(type, BaseCondition.NOT_LIKE)) {
                    sql.and().notLikePrefix(fieldName, (String) value);
                } else if (StrUtil.equals(type, BaseCondition.IN)) {
                    sql.and().in(fieldName, (List<?>) value);
                } else if (StrUtil.equals(type, BaseCondition.REGEX)) {
                    Pattern pattern = (Pattern) value;
                    sql.and().regexp(pattern.pattern());
                }
            }
            return sql;
        }
    }
}
