package gitee.com.ericfox.ddd.infrastructure.general.toolkit.api;

import gitee.com.ericfox.ddd.infrastructure.general.common.constants.ActiveProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class ResBuilder {
    public static class defValue {
        public static ResBuilder created() {
            return ResBuilder.hashMapData().status(201);
        }

        public static ResBuilder success() {
            return ResBuilder.hashMapData().message("请求成功").status(200);
        }

        public static ResBuilder badRequest(Exception e) {
            log.warn(e.getMessage(), e);
            return ResBuilder.hashMapData().message("传入参数错误").error(e).status(400);
        }

        public static ResBuilder unauthorized() {
            Exception e = new Exception("未获得权限认证");
            log.error(e.getMessage(), e);
            return ResBuilder.hashMapData().message("未获得权限认证").status(401);
        }

        public static ResBuilder forbidden(Exception e) {
            log.error(e.getMessage(), e);
            return ResBuilder.hashMapData().message("权限不够，请勿重复请求").status(403);
        }

        public static ResBuilder internalServerError(Exception e) {
            log.error(e.getMessage(), e);
            return ResBuilder.hashMapData().message("服务器错误，请联系开发人员").error(e).status(500);
        }

        public static ResBuilder serviceUnavailable(Exception e) {
            log.error(e.getMessage(), e);
            return ResBuilder.hashMapData().message("服务维护中，请联系开发人员").error(e).status(503);
        }
    }

    private final String errKey;
    private final String msgKey;
    private Serializable data;
    private HttpStatus statusCode = HttpStatus.OK;
    private MultiValueMap<String, String> headers;

    ResBuilder(Serializable data) {
        this.errKey = ActiveProperties.customProperties.getResponse().getKeyOfErrorCode();
        this.msgKey = ActiveProperties.customProperties.getResponse().getKeyOfErrorMessage();
        this.data = data;
    }

    public static ResBuilder blobData(Serializable data) {
        return new ResBuilder(data);
    }

    /**
     * @return new ResBuilder(new HashMap<String, Object>());
     */
    public static ResBuilder hashMapData() {
        return new ResBuilder(new HashMap<String, Object>());
    }

    /**
     * @return new ResBuilder(new TreeMap<String, Object>());
     */
    public static ResBuilder treeMapData() {
        return new ResBuilder(new TreeMap<String, Object>());
    }

    /**
     * @return new ResBuilder(null)
     */
    public static ResBuilder noData() {
        return new ResBuilder(null);
    }

    public ResBuilder setHeaders(MultiValueMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * 向ResponseBody存入数据
     *
     * @param key   键
     * @param value 值
     * @return this
     */
    public ResBuilder put(String key, Object value) {
        if (this.data == null) {
            hashMapData();
        }
        if (this.data instanceof Map) {
            ((Map) this.data).put(key, value);
        } else {
            Class<?> c = data.getClass();
            try {
                Field field = c.getDeclaredField(key);
                field.setAccessible(true);
                field.set(data, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage());
                error(e.getMessage());
            }
        }
        return this;
    }

    /**
     * 返回ResponseBody
     *
     * @return Serializable
     */
    public Serializable getOriginData() {
        return data;
    }

    /**
     * 返回JSON格式的ResponseBody
     *
     * @return String
     */
    public String getJsonData() {
        return JsonUtil.toJsonStr(data);
    }

    public ResBuilder message(String str) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        put(msgKey, str);
        return this;
    }

    /**
     * 设置异常信息
     *
     * @param str 错误信息
     * @return this
     */
    public ResBuilder error(String str) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        put(errKey, str);
        return this;
    }

    public ResBuilder error(Exception e) {
        return error(e.getMessage());
    }

    /**
     * 设置相应状态
     *
     * @param statusCode 200 请求成功
     *                   *                   201 已创建
     *                   *                   204 无内容
     *                   *                   400 错误请求
     *                   *                   401 未经授权
     *                   *                   403 禁止请求
     *                   *                   404 未找到资源
     *                   *                   500 服务器错误
     *                   *                   501 未实现功能
     *                   *                   502 网关错误
     *                   *                   503 服务无法获得/维护中
     *                   *                   504 网关超时
     * @return this
     */
    public ResBuilder status(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * @param statusCode HTTP状态码
     * @return this
     */
    public ResBuilder status(int statusCode) {
        this.statusCode = HttpStatus.resolve(statusCode);
        return this;
    }

    /**
     * @return 根据内容及状态码创建一个ResponseEntity实体
     */
    public ResponseEntity<?> build() {
        return new ResponseEntity(data, headers, statusCode);
    }
}
