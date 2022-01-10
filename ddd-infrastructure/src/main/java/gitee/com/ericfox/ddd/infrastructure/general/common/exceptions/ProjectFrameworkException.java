package gitee.com.ericfox.ddd.infrastructure.general.common.exceptions;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.ProjectException;

/**
 * 持久化异常
 */
public class ProjectFrameworkException extends RuntimeException implements ProjectException<ProjectFrameworkException> {
    public ProjectFrameworkException(String msg) {
        super(msg);
    }

    public ProjectFrameworkException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
