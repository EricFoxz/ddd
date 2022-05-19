package gitee.com.ericfox.ddd.infrastructure.general.common.exceptions;

import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 持久化异常
 */
public class ProjectRepoException extends ProjectFrameworkException {
    public ProjectRepoException(String msg, HttpStatus httpStatus) {
        super(msg, httpStatus);
    }

    public ProjectRepoException(String msg, HttpStatus httpStatus, Throwable throwable) {
        super(msg, httpStatus, throwable);
    }
}
