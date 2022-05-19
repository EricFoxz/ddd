package gitee.com.ericfox.ddd.infrastructure.general.config.exception;

import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class ProjectExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ProjectFrameworkException.class)
    public ResponseEntity<?> handlerException(HttpServletRequest request, ProjectFrameworkException exception) {
        ResponseEntity<?> responseEntity = exception.getResponseEntity();
        log.error(exception.getMessage(), exception);
        return responseEntity;
    }
}
