package gitee.com.ericfox.ddd.common.interfaces.apis;

import org.springframework.http.HttpStatus;

/**
 * http请求状态码
 */
public interface BaseHttpStatus {
    static HttpStatus CONTINUE_100 = HttpStatus.CONTINUE;
    static HttpStatus OK_200 = HttpStatus.OK;
    static HttpStatus CREATED_201 = HttpStatus.CREATED;
    static HttpStatus NO_CONTENT_204 = HttpStatus.NO_CONTENT;
    static HttpStatus RESET_CONTENT_205 = HttpStatus.RESET_CONTENT;
    static HttpStatus MOVED_PERMANENTLY_301 = HttpStatus.MOVED_PERMANENTLY;
    static HttpStatus FOUND_302 = HttpStatus.FOUND;
    static HttpStatus NOT_MODIFIED_304 = HttpStatus.NOT_MODIFIED;
    static HttpStatus UNAUTHORIZED_401 = HttpStatus.UNAUTHORIZED;
    static HttpStatus FORBIDDEN_403 = HttpStatus.FORBIDDEN;
    static HttpStatus NOT_FOUND_404 = HttpStatus.NOT_FOUND;
    static HttpStatus METHOD_NOT_ALLOWED_405 = HttpStatus.METHOD_NOT_ALLOWED;
    static HttpStatus UNPROCESSABLE_ENTITY_422 = HttpStatus.UNPROCESSABLE_ENTITY;
    static HttpStatus TOO_MANY_REQUESTS_429 = HttpStatus.TOO_MANY_REQUESTS;
    static HttpStatus INTERNAL_SERVER_ERROR_500 = HttpStatus.INTERNAL_SERVER_ERROR;
    static HttpStatus BAD_GATEWAY_502 = HttpStatus.BAD_GATEWAY;
    static HttpStatus SERVICE_UNAVAILABLE_503 = HttpStatus.SERVICE_UNAVAILABLE;
    static HttpStatus GATEWAY_TIMEOUT_504 = HttpStatus.GATEWAY_TIMEOUT;
}
