package gitee.com.ericfox.ddd.domain.gen.common.constants;

import java.sql.Timestamp;
import java.util.Date;

public class ReflectClassNameConstants {
    public final static String SHORT = Short.class.getName();
    public final static String INTEGER = Integer.class.getName();
    public final static String LONG = Long.class.getName();
    public final static String BOOLEAN = Boolean.class.getName();
    public final static String STRING = String.class.getName();
    public final static String CHARACTER = Character.class.getName();
    public final static String FLOAT = Float.class.getName();
    public final static String DOUBLE = Double.class.getName();

    /**
     * 日期类型的数据不建议存为Date，应改为Long
     */
    @Deprecated
    public final static String DATE = Date.class.getName();
    /**
     * 日期类型的数据不建议存为Timestamp，应改为Long
     */
    @Deprecated
    public final static String TIMESTAMP = Timestamp.class.getName();
}
