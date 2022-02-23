package gitee.com.ericfox.ddd.infrastructure.service.util;

import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.general.common.framework.pojo.OkHttpResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class OkHttpService {
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML_MEDIA_TYPE = MediaType.parse("application/xml; charset=utf-8");

    @Resource
    private OkHttpClient okHttpClient;

    //=====================================业务处理 start=====================================

    public OkHttpResponse get(String url) {
        Request request = new Request.Builder().url(url).build();
        return getResponse(request);
    }

    public OkHttpResponse get(String url, Map<String, String> queries, Map<String, String> headers) {
        StringBuilder fullUrl = new StringBuilder(url);
        if (queries != null && queries.keySet().size() > 0) {
            fullUrl.append("?");

            queries.forEach((key, value) -> {
                if (StrUtil.isNotBlank(value) && !StrUtil.equalsIgnoreCase(value, "null")) {
                    fullUrl.append(key).append("=").append(value).append("&");
                }
            });

            fullUrl.deleteCharAt(fullUrl.length() - 1);
        }

        Request.Builder builderRequest = new Request.Builder();
        builderRequest.addHeader("Connection", "close");//避免不时出现的错误：okhttp error = java.io.IOException: unexpected end of stream on Connection

        if (headers != null && headers.keySet().size() > 0) {
            headers.forEach(builderRequest::addHeader);
        }

        Request request = builderRequest.url(fullUrl.toString()).build();
        return getResponse(request);
    }

    public OkHttpResponse post(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            params.forEach(builder::add);
        }

        Request.Builder builderRequest = new Request.Builder();
        builderRequest.addHeader("Connection", "close");//避免不时出现的错误：okhttp error = java.io.IOException: unexpected end of stream on Connection

        if (headers != null && headers.keySet().size() > 0) {
            headers.forEach(builderRequest::addHeader);
        }

        Request request = builderRequest.url(url).post(builder.build()).build();
        return getResponse(request);
    }

    /**
     * Post 请求发送 JSON 数据
     */
    public OkHttpResponse postJsonParams(String url, String jsonParams, Map<String, String> headers) {
        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, jsonParams);
        Request request = buildPostRequestBody(url, requestBody, headers);
        return getResponse(request);
    }

    /**
     * Post请求发送xml数据
     */
    public OkHttpResponse postXmlParams(String url, String xml, Map<String, String> headers) {
        RequestBody requestBody = RequestBody.create(XML_MEDIA_TYPE, xml);
        Request request = buildPostRequestBody(url, requestBody, headers);
        return getResponse(request);
    }

    /**
     * 带 Basic 开头
     */
    public void basicAuthorization(Map<String, String> headers, String username, String password) {
        headers.put("Authorization", Credentials.basic(username, password));
    }

    /**
     * 带 Bearer 开头
     */
    public void bearerAuthorization(Map<String, String> headers, String token) {
        headers.put("Authorization", "Bearer " + token);
    }

    //=====================================业务处理  end=====================================

    //=====================================私有方法 start=====================================

    private Request buildPostRequestBody(String url, RequestBody requestBody, Map<String, String> headers) {
        Request.Builder builderRequest = new Request.Builder();
        builderRequest.addHeader("Connection", "close");//避免不时出现的错误：okhttp error = java.io.IOException: unexpected end of stream on Connection
        if (headers != null && headers.keySet().size() > 0) {
            headers.forEach(builderRequest::addHeader);
        }
        return builderRequest.url(url).post(requestBody).build();
    }

    private OkHttpResponse getResponse(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            OkHttpResponse okHttpResponse = new OkHttpResponse();
            okHttpResponse.setStatus(response.code());
            okHttpResponse.setResponse(response.body().string());
            return okHttpResponse;
        } catch (Exception e) {
            log.error("okHttpService::getResponse okhttp error = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    //=====================================私有方法  end=====================================

}
