package gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;

import java.util.List;

public class SQL {
    private final StringBuilder stringBuilder = new StringBuilder();
    private final List<Object> paramList = CollUtil.newArrayList();

    private SQL() {
    }

    public static SQL getInstance(String sql) {
        SQL instance = new SQL();
        if (StrUtil.isNotBlank(sql)) {
            instance.stringBuilder.append(sql);
        }
        return instance;
    }

    public static SQL getInstance() {
        return getInstance(null);
    }

    /**
     * --------------------- 基本关键字 ---------------------
     */
    public SQL where() {
        stringBuilder.append(" WHERE ");
        return this;
    }

    public SQL matchAll() {
        stringBuilder.append(" 1 = 1 ");
        return this;
    }

    public SQL matchNothing() {
        stringBuilder.append(" 1 != 1 ");
        return this;
    }

    public SQL and() {
        stringBuilder.append(" AND ");
        return this;
    }

    public SQL and(SQL sql) {
        stringBuilder.append(" AND (").append(sql.toString()).append(") ");
        paramList.addAll(sql.getParamList());
        return this;
    }

    public SQL or() {
        stringBuilder.append(" OR ");
        return this;
    }

    public SQL or(SQL sql) {
        stringBuilder.append(" OR (").append(sql.toString()).append(") ");
        paramList.addAll(sql.getParamList());
        return this;
    }

    public SQL not() {
        stringBuilder.append(" NOT ");
        return this;
    }

    public SQL isNull(String field) {
        stringBuilder.append(" ").append(field).append(" IS NULL ");
        return this;
    }

    public SQL isNull() {
        stringBuilder.append(" IS NULL ");
        return this;
    }

    public SQL isNotNull(String field) {
        stringBuilder.append(" ").append(field).append(" IS NOT NULL ");
        return this;
    }

    public SQL isNotNull() {
        stringBuilder.append(" IS NOT NULL ");
        return this;
    }

    public SQL equal(String field, Object value) {
        stringBuilder.append(" ").append(field).append(" = ? ");
        paramList.add(value);
        return this;
    }

    public SQL equal() {
        stringBuilder.append(" = ");
        return this;
    }

    public SQL notEqual(String field, Object value) {
        stringBuilder.append(" ").append(field).append(" != ? ");
        paramList.add(value);
        return this;
    }

    public SQL notEqual() {
        stringBuilder.append(" != ");
        return this;
    }

    public SQL greatThan(String field, Object value) {
        stringBuilder.append(" ").append(field).append(" > ? ");
        paramList.add(value);
        return this;
    }

    public SQL greatThan() {
        stringBuilder.append(" > ");
        return this;
    }

    public SQL greatThanEqual(String field, Object value) {
        stringBuilder.append(" ").append(field).append(" >= ? ");
        paramList.add(value);
        return this;
    }

    public SQL greatThanEqual() {
        stringBuilder.append(" >= ");
        return this;
    }

    public SQL lessThan(String field, Object value) {
        stringBuilder.append(" ").append(field).append(" < ? ");
        paramList.add(value);
        return this;
    }

    public SQL lessThan() {
        stringBuilder.append(" < ");
        return this;
    }

    public SQL lessThanEqual(String field, Object value) {
        stringBuilder.append(" ").append(field).append(" <= ? ");
        paramList.add(value);
        return this;
    }

    public SQL lessThanEqual() {
        stringBuilder.append(" <= ");
        return this;
    }

    public SQL between(String field, Object minValue, Object maxValue) {
        stringBuilder.append(" BETWEEN ? AND ? ");
        paramList.add(minValue);
        paramList.add(maxValue);
        return this;
    }

    public SQL between() {
        stringBuilder.append(" BETWEEN ");
        return this;
    }

    public SQL notLikePrefix(String field, String prefix) {
        prefix = StrUtil.replace(prefix, "%", "\\\\%");
        prefix = StrUtil.replace(prefix, "_", "\\\\_");
        stringBuilder.append(" ").append(field).append(" NOT LIKE '").append(prefix).append("%'");
        return this;
    }

    public SQL likePrefix(String field, String prefix) {
        prefix = StrUtil.replace(prefix, "%", "\\\\%");
        prefix = StrUtil.replace(prefix, "_", "\\\\_");
        stringBuilder.append(" ").append(field).append(" LIKE '").append(prefix).append("%'");
        return this;
    }

    public SQL like() {
        stringBuilder.append(" LIKE ");
        return this;
    }

    public SQL regexp(String regexp) {
        stringBuilder.append(" REGEXP ? ");
        paramList.add(regexp);
        return this;
    }

    public SQL regexp() {
        stringBuilder.append(" REGEXP ");
        return this;
    }

    public SQL in(String field, List<?> list) {
        stringBuilder.append(" ").append(field).append(" IN ? ");
        paramList.add(list);
        return this;
    }

    public SQL in() {
        stringBuilder.append(" IN ");
        return this;
    }

    /**
     * --------------------- 函数 ---------------------
     */
    public SQL ifCase(String condition, String ifTrue, String ifFalse) {
        stringBuilder.append(" IF(").append(condition).append(", ?, ?) ");
        paramList.add(ifTrue);
        paramList.add(ifFalse);
        return this;
    }

    /**
     * --------------------- 聚合 ---------------------
     */
    public SQL count(String field) {
        stringBuilder.append(" COUNT(").append(field).append(") ");
        return this;
    }

    public SQL max(String field) {
        stringBuilder.append(" MAX(").append(field).append(") ");
        return this;
    }

    public SQL groupBy(String str) {
        stringBuilder.append(" GROUP BY ").append(str).append(" ");
        return this;
    }

    public SQL groupBy() {
        stringBuilder.append(" GROUP BY ");
        return this;
    }

    /**
     * --------------------- 输出 ---------------------
     */
    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public List<Object> getParamList() {
        return paramList;
    }

}
