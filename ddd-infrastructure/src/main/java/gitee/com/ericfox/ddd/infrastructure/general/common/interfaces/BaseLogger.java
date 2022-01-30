package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

public interface BaseLogger {
    void info(String msg);

    void warn(String msg);

    void debug(String msg);

    void error(String msg);
}
