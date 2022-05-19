package gitee.com.ericfox.ddd.common.interfaces.infrastructure;

import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * 项目Exception
 */
public interface ProjectException<T extends ProjectException<T>> {
    ResponseEntity<?> getResponseEntity();
    void setResponseEntity(ResponseEntity<?> responseEntity);
}
