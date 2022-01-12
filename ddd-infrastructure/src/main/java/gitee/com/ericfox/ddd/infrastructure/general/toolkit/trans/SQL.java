package gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans;

public interface SQL {

    static String where() {
        return " WHERE ";
    }

    static String matchAll() {
        return " 1 = 1 ";
    }

    static String matchNothing() {
        return " 1 != 1 ";
    }

    static String and() {
        return " AND ";
    }

    static String or() {
        return " OR ";
    }

    static String not() {
        return " NOT ";
    }

    static String isNull() {
        return " IS NULL ";
    }

    static String isNotNull() {
        return " IS NOT NULL ";
    }

    static String equal() {
        return " = ";
    }

    static String notEqual() {
        return " != ";
    }

    static String greatThan() {
        return " > ";
    }

    static String greatThanEqual() {
        return " >= ";
    }

    static String lessThan() {
        return " < ";
    }

    static String lessThanEqual() {
        return " <= ";
    }

    static String likePrefix(String prefix) {
        return " like '" + prefix + "%'";
    }

    static String regexp(String reg) {
        return " REGEXP '" + reg + "' ";
    }

    /**
     * --------------------- 条件 ---------------------
     */
    static String ifCase(String condition, String ifTrue, String ifFalse) {
        return " IF(" + condition + ", " + ifTrue + ", " + ifFalse + ") ";
    }

    /**
     * --------------------- 聚合 ---------------------
     */
    static String count(String field) {
        return " COUNT(" + field + ") ";
    }

    static String max(String field) {
        return " MAX(" + field + ") ";
    }

    static String groupBy(String str) {
        return " GROUP BY " + str + " ";
    }
}
