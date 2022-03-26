package gitee.com.ericfox.ddd.common.exceptions;

import gitee.com.ericfox.ddd.common.interfaces.infrastructure.ProjectException;

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
