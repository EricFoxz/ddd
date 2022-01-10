package gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans;

import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleCondition implements BaseCondition<SimpleCondition> {
    private final Map<String, Object> condition = MapUtil.newConcurrentHashMap(4);

    public static SimpleCondition newInstance(BasePo<?> obj) {
        SimpleCondition simpleCondition = newInstance();
        simpleCondition.init(obj);
        return simpleCondition;
    }

    public static SimpleCondition newInstance() {
        return new SimpleCondition();
    }

    private void init(BasePo<?> obj) {
        Map<String, Object> record = BeanUtil.beanToMap(obj);
        if (CollUtil.isNotEmpty(record)) {
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                appendCondition(entry.getKey(), EQUALS, entry.getValue());
            }
        }
    }

    private Map<String, Object> getCondition() {
        return condition;
    }

    private SimpleCondition appendCondition(String field, String type, Object value) {
        if (StrUtil.isBlank(field)) {
            field = "";
        }
        condition.put(field + SEPARATOR + type, value);
        return this;
    }

    @Override
    public SimpleCondition equals(String field, Object value) {
        return appendCondition(field, EQUALS, value);
    }

    @Override
    public SimpleCondition notEquals(String field, Object value) {
        return appendCondition(field, NOT_EQUALS, value);
    }

    @Override
    public SimpleCondition isNull(String field) {
        return appendCondition(field, IS_NULL, "");
    }

    @Override
    public SimpleCondition isNotNull(String field) {
        return appendCondition(field, IS_NOT_NULL, "");
    }

    @Override
    public SimpleCondition moreThan(String field, Object value) {
        return appendCondition(field, MORE_THAN, value);
    }

    @Override
    public SimpleCondition moreThanOrEquals(String field, Object value) {
        return appendCondition(field, MORE_THAN_OR_EQUALS, value);
    }

    @Override
    public SimpleCondition lessThan(String field, Object value) {
        return appendCondition(field, LESS_THAN, value);
    }

    @Override
    public SimpleCondition lessThanOrEquals(String field, Object value) {
        return appendCondition(field, LESS_THAN_OR_EQUALS, value);
    }

    @Override
    public SimpleCondition between(String field, Object v1, Object v2) {
        return appendCondition(field, BETWEEN, CollUtil.newArrayList(v1, v2));
    }

    @Override
    public SimpleCondition like(String field, Object value) {
        return appendCondition(field, LIKE, value);
    }

    @Override
    public SimpleCondition notLike(String field, Object value) {
        return appendCondition(field, NOT_LIKE, value);
    }

    @Override
    public SimpleCondition match(String field, String regex) {
        return appendCondition(field, MATCH, regex);
    }

    @Override
    public <M extends Serializable> SimpleCondition in(String field, List<M> list) {
        return appendCondition(field, IN, list);
    }

    @Override
    public SimpleCondition or(BaseCondition<?> condition) {
        return appendCondition("", OR, condition);
    }

    @Override
    public SimpleCondition and(BaseCondition<?> condition) {
        return appendCondition("", AND, condition);
    }

    @Override
    public SimpleCondition removeCondition(String field) {
        return removeCondition(field, "");
    }

    @Override
    public SimpleCondition removeCondition(String field, String type) {
        if (StrUtil.isBlank(field) && StrUtil.isBlank(type)) {
            log.error("simpleCondition如果想要移除所有条件请使用removeAllCondition()方法");
            throw new ProjectFrameworkException("simpleCondition如果想要移除所有条件请使用removeAllCondition()方法");
        }
        if (StrUtil.isBlank(field)) { //移除所有指定field的条件
            String s = field + SEPARATOR;
            for (String key : condition.keySet()) {
                if (key.startsWith(s)) {
                    condition.remove(key);
                }
            }
        } else if (StrUtil.isBlank(field)) { //移除所有指定type的条件
            String s = SEPARATOR + type;
            for (String key : condition.keySet()) {
                if (key.endsWith(s)) {
                    condition.remove(key);
                }
            }
        } else {
            String key = field + SEPARATOR + type;
            condition.remove(key);
        }
        return null;
    }

    @Override
    public SimpleCondition removeAllCondition() {
        condition.clear();
        return this;
    }
}
