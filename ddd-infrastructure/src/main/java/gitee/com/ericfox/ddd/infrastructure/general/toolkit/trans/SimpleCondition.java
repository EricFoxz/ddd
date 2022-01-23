package gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans;

import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
            record.forEach((key, value) -> {
                if (value != null) {
                    appendCondition(key, EQUALS, value);
                }
            });
        }
    }

    public Map<String, Object> getConditionMap() {
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
    public SimpleCondition matchAll() {
        return appendCondition("", MATCH_ALL, "");
    }

    @Override
    public SimpleCondition matchNothing() {
        return appendCondition("", MATCH_NOTHING, "");
    }

    @Override
    public SimpleCondition equals(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, EQUALS, value);
    }

    @Override
    public SimpleCondition notEquals(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, NOT_EQUALS, value);
    }

    @Override
    public SimpleCondition isNull(@NonNull String field) {
        return appendCondition(field, IS_NULL, "");
    }

    @Override
    public SimpleCondition isNotNull(@NonNull String field) {
        return appendCondition(field, IS_NOT_NULL, "");
    }

    @Override
    public SimpleCondition moreThan(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, GREAT_THAN, value);
    }

    @Override
    public SimpleCondition moreThanOrEquals(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, GREAT_THAN_OR_EQUALS, value);
    }

    @Override
    public SimpleCondition lessThan(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, LESS_THAN, value);
    }

    @Override
    public SimpleCondition lessThanOrEquals(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, LESS_THAN_OR_EQUALS, value);
    }

    @Override
    public SimpleCondition between(@NonNull String field, @NonNull Object v1, @NonNull Object v2) {
        if (!v1.getClass().equals(v2.getClass())) {
            log.error("simpleCondition:between输入的两个参数必须是同一类型");
            throw new ProjectFrameworkException("simpleCondition:between输入的两个参数必须是同一类型");
        }
        return appendCondition(field, BETWEEN, CollUtil.newArrayList(v1, v2));
    }

    @Override
    @NonNull
    public SimpleCondition like(@NonNull String field, @NonNull String value) {
        return appendCondition(field, LIKE, value);
    }

    @Override
    public SimpleCondition notLike(@NonNull String field, @NonNull Object value) {
        return appendCondition(field, NOT_LIKE, value);
    }

    @Override
    public SimpleCondition regex(@NonNull String field, @NonNull Pattern regex) {
        return appendCondition(field, REGEX, regex);
    }

    @Override
    public SimpleCondition in(@NonNull String field, @NonNull List<?> list) {
        if (CollUtil.isEmpty(list)) {
            return appendCondition("", MATCH_NOTHING, "");
        }
        return appendCondition(field, IN, list);
    }

    @Override
    public SimpleCondition or(@NonNull BaseCondition<?> condition) {
        return appendCondition("", OR, condition);
    }

    @Override
    public SimpleCondition and(@NonNull BaseCondition<?> condition) {
        return appendCondition("", AND, condition);
    }

    @Override
    public SimpleCondition removeCondition(@NonNull String field) {
        return removeCondition(field, "");
    }

    @Override
    public SimpleCondition removeCondition(@NonNull String field, String type) {
        if (StrUtil.isBlank(field) && StrUtil.isBlank(type)) {
            log.error("simpleCondition如果想要移除所有条件请使用removeAllCondition()方法");
            throw new ProjectFrameworkException("simpleCondition如果想要移除所有条件请使用removeAllCondition()方法");
        }
        if (StrUtil.isBlank(field)) { //移除所有指定field的条件
            String s = field + SEPARATOR;
            condition.keySet().forEach(key -> {
                if (key.startsWith(s)) {
                    condition.remove(key);
                }
            });
        } else if (StrUtil.isBlank(field)) { //移除所有指定type的条件
            String s = SEPARATOR + type;
            condition.keySet().forEach(key -> {
                if (key.endsWith(s)) {
                    condition.remove(key);
                }
            });
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
