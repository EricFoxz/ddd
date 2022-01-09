package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

public interface BaseCondition<T extends BaseCondition<T>> {
    void moreThan(String field, Object value);

    void moreThanOrEquals(String field, Object value);

    void lessThan(String field, Object value);

    void lessThanOrEquals(String field, Object value);

    void between(String field, Object v1, Object v2);

    void like(String field, Object value);

    void and(T t);

    void or(T t);
}
