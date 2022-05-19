package gitee.com.ericfox.ddd.common.exceptions;

import gitee.com.ericfox.ddd.common.interfaces.infrastructure.ProjectException;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * 持久化异常
 */
@Getter
@Setter
public class ProjectFrameworkException extends RuntimeException implements ProjectException<ProjectFrameworkException> {
    private ResponseEntity<?> responseEntity;

    public ProjectFrameworkException(String msg, HttpStatus httpStatus) {
        super(msg);
        Map<String, Object> body = MapUtil.newHashMap(2);
        body.put("message", msg);
        body.put("code", httpStatus.value());
        this.responseEntity = new ResponseEntity(body, httpStatus);
    }

    public ProjectFrameworkException(String msg, HttpStatus httpStatus, Throwable throwable) {
        super(msg, throwable);
        Map<String, Object> body = MapUtil.newHashMap(2);
        body.put("message", msg);
        body.put("code", httpStatus.value());
        this.responseEntity = new ResponseEntity(body, httpStatus);
    }
}
