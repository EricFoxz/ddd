package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

public enum HttpStatusEnum implements BaseEnum<HttpStatusEnum, Integer> {
    OK(200, "OK"),//请求成功并返回实体资源
    CREATED(201, "Created"),//创建资源成功
    UPDATE(202, "Update"),//更新资源
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    //    PARTIAL_CONTENT(206, "Partial Content"),
//    MOVED_PERMANENTLY(301, "Moved Permanently"),
//    FOUND(302, "Found"),
//    SEE_OTHER(303, "See Other"),
//    NOT_MODIFIED(304, "Not Modified"),
//    USE_PROXY(305, "Use Proxy"),
//    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    BAD_REQUEST(400, "Bad Request"),//一般是参数错误
    UNAUTHORIZED(401, "Unauthorized"),//一般用户验证失败（用户名、密码错误等）
    //    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),//一般用户权限校验失败
    NOT_FOUND(404, "Not Found"),//资源不存在（github在权限校验失败的情况下也会返回404，为了防止一些私有接口泄露出去）
    //    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
//    NOT_ACCEPTABLE(406, "Not Acceptable"),
//    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
//    REQUEST_TIMEOUT(408, "Request Timeout"),
//    CONFLICT(409, "Conflict"),
//    GONE(410, "Gone"),
//    LENGTH_REQUIRED(411, "Length Required"),
//    PRECONDITION_FAILED(412, "Precondition Failed"),
//    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
//    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
//    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
//    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
//    EXPECTATION_FAILED(417, "Expectation Failed"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"), //一般是必要字段缺失或参数格式化问题
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"); //服务器未知错误
//    NOT_IMPLEMENTED(501, "Not Implemented"),
//    BAD_GATEWAY(502, "Bad Gateway"),
//    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
//    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
//    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

    public int code;

    public String description;

    HttpStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public HttpStatusEnum[] getEnums() {
        return HttpStatusEnum.values();
    }
}
