package gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework;

import gitee.com.ericfox.ddd.common.enums.db.DbDataStructureEnum;
import gitee.com.ericfox.ddd.common.enums.db.DbIndexTypeEnum;

import java.lang.annotation.*;

/**
 * 表索引
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableIndex {
    /**
     * 索引名称
     */
    String name();

    /**
     * 字段名
     */
    String[] column();

    /**
     * 索引类型
     */
    DbIndexTypeEnum dbIndexTypeEnum() default DbIndexTypeEnum.NORMAL;

    /**
     * 数据结构
     */
    DbDataStructureEnum dbDataTypeEnum() default DbDataStructureEnum.B_TREE;
}
