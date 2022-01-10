package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import java.io.Serializable;
import java.util.List;

public interface BaseCondition<T extends BaseCondition<T>> {
    String SEPARATOR = ":";

    String EQUALS = "EQUALS";
    String NOT_EQUALS = "NOT_EQUALS";
    String IS_NULL = "NOT_EQUALS";
    String IS_NOT_NULL = "NOT_EQUALS";
    String MORE_THAN = "MORE_THAN";
    String MORE_THAN_OR_EQUALS = "MORE_THAN_OR_EQUALS";
    String LESS_THAN = "LESS_THAN";
    String LESS_THAN_OR_EQUALS = "LESS_THAN_OR_EQUALS";
    String BETWEEN = "BETWEEN";
    String LIKE = "LIKE";
    String NOT_LIKE = "NOT_LIKE";
    String MATCH = "MATCH";
    String IN = "IN";
    String OR = "OR";
    String AND = "AND";

    T equals(String field, Object value);

    T notEquals(String field, Object value);

    T isNull(String field);

    T isNotNull(String field);

    T moreThan(String field, Object value);

    T moreThanOrEquals(String field, Object value);

    T lessThan(String field, Object value);

    T lessThanOrEquals(String field, Object value);

    T between(String field, Object v1, Object v2);

    T like(String field, Object value);

    T notLike(String field, Object value);

    T match(String field, String regex);

    <M extends Serializable> T in(String field, List<M> list);

    T and(BaseCondition<?> condition);

    T or(BaseCondition<?> condition);

    T removeCondition(String field);

    T removeCondition(String field, String type);

    T removeAllCondition();
}
