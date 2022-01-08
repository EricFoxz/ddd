package gitee.com.ericfox.ddd.infrastructure.general.common.exceptions;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.ProjectException;

/**
 * 持久化异常
 */
public class ProjectRepoException extends RuntimeException implements ProjectException<ProjectRepoException> {
    public ProjectRepoException(String msg) {
        super(msg);
    }

    public ProjectRepoException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
