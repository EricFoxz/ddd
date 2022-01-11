package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface BaseCondition<T extends BaseCondition<T>> {
    char SEPARATOR = ':';

    String EQUALS = "EQUALS";
    String NOT_EQUALS = "NOT_EQUALS";
    String IS_NULL = "IS_NULL";
    String IS_NOT_NULL = "IS_NOT_NULL";
    String MORE_THAN = "MORE_THAN";
    String MORE_THAN_OR_EQUALS = "MORE_THAN_OR_EQUALS";
    String LESS_THAN = "LESS_THAN";
    String LESS_THAN_OR_EQUALS = "LESS_THAN_OR_EQUALS";
    String BETWEEN = "BETWEEN";
    String LIKE = "LIKE";
    String NOT_LIKE = "NOT_LIKE";
    String REGEX = "REGEX";
    String IN = "IN";
    String OR = "OR";
    String AND = "AND";
    String MATCH_ALL = "MATCH_ALL";
    String MATCH_NOTHING = "MATCH_NOTHING";

    /**
     * 获取当前对象构建的condition合集
     */
    Map<String, Object> getConditionMap();

    /**
     * 匹配所有
     */
    T matchAll();

    /**
     * 不匹配任何记录
     */
    T matchNothing();

    /**
     * 等于
     */
    T equals(@NonNull String field, @NonNull Object value);

    /**
     * 不等于
     */
    T notEquals(@NonNull String field, @NonNull Object value);

    /**
     * 是空
     */
    T isNull(@NonNull String field);

    /**
     * 非空
     */
    T isNotNull(@NonNull String field);

    /**
     * 大于
     */
    T moreThan(@NonNull String field, @NonNull Object value);

    /**
     * 大于等于
     */
    T moreThanOrEquals(@NonNull String field, @NonNull Object value);

    /**
     * 小于
     */
    T lessThan(@NonNull String field, @NonNull Object value);

    /**
     * 小于等于
     */
    T lessThanOrEquals(@NonNull String field, @NonNull Object value);

    /**
     * 同sql的between
     */
    T between(@NonNull String field, @NonNull Object v1, @NonNull Object v2);

    /**
     * 符合prefix这个前缀
     */
    T like(@NonNull String field, @NonNull String prefix);

    /**
     * 不以prefix作为前缀的
     */
    T notLike(@NonNull String field, @NonNull Object value);

    /**
     * 匹配正则，不推荐使用，因为持久化层不一定
     * TODO-待验证 可能在转化impl的时候有bug
     */
    T regex(@NonNull String field, @NonNull Pattern regex);

    /**
     * 在集合中
     */
    T in(@NonNull String field, @NonNull List<?> list);

    /**
     * 增加condition，相当于 AND ( conditions )
     */
    T and(@NonNull BaseCondition<?> condition);

    /**
     * 增加condition，相当于 OR ( conditions )
     */
    T or(@NonNull BaseCondition<?> condition);

    /**
     * 移除关于某字段的condition
     */
    T removeCondition(@NonNull String field);

    /**
     * 移除关于某字段某一类型的condition
     */
    T removeCondition(@NonNull String field, String type);

    /**
     * 移除所有condition
     */
    T removeAllCondition();
}
