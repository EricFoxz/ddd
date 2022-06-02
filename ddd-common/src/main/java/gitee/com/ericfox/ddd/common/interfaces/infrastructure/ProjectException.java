package gitee.com.ericfox.ddd.common.interfaces.infrastructure;

import org.springframework.http.ResponseEntity;

/**
 * 项目Exception
 */
public interface ProjectException<T extends ProjectException<T>> {
    ResponseEntity<?> getResponseEntity();

    void setResponseEntity(ResponseEntity<?> responseEntity);
}
