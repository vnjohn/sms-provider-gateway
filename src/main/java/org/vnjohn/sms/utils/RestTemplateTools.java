package org.vnjohn.sms.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.Map;

/**
 * RestTemplateTools 请求体类
 *
 * @author vnjohn
 * @since 2023/06/02
 */
@Slf4j
public class RestTemplateTools {

    private static final int DEFAULT_TIME_OUT = 60 * 1000;

    private RestTemplateTools() {
    }


    /**
     * 支持 Https 请求，配置请求超时时长
     *
     * @return 请求组件实体工厂
     */
    private static HttpComponentsClientHttpRequestFactory generateHttpsRequestFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory =
                    new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(DEFAULT_TIME_OUT);
            factory.setReadTimeout(DEFAULT_TIME_OUT);
            factory.setConnectionRequestTimeout(DEFAULT_TIME_OUT);
            return factory;
        } catch (Exception e) {
            log.error("创建 HttpsRestTemplate 失败", e);
            throw new RuntimeException("创建 HttpsRestTemplate 失败", e);
        }
    }


    private static final class SingletonRestTemplateHolder {
        static final RestTemplate SINGLETON_REST_TEMPLATE = new RestTemplate(generateHttpsRequestFactory());
    }

    public static RestTemplate getInstance() {
        return SingletonRestTemplateHolder.SINGLETON_REST_TEMPLATE;
    }


    // ----------------------------------GET-------------------------------------------------------

    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        return getInstance().getForEntity(url, responseType);
    }

    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, Class<T> responseType, Object... uriVariables) {
        return getInstance().getForEntity(url, responseType, uriVariables);
    }

    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return getInstance().getForEntity(url, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return get(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType, Object... uriVariables) {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return get(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------POST-------------------------------------------------------

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return
     */
    public static <T> ResponseEntity<T> post(String url, Class<T> responseType) {
        return getInstance().postForEntity(url, HttpEntity.EMPTY, responseType);
    }

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType) {
        return getInstance().postForEntity(url, requestBody, responseType);
    }

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        return getInstance().postForEntity(url, requestBody, responseType, uriVariables);
    }

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        return getInstance().postForEntity(url, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return getInstance().exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return getInstance().exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------PUT-------------------------------------------------------

    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, Class<T> responseType, Object... uriVariables) {
        return put(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        return put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        return put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return put(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return put(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的PUT请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return getInstance().exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的PUT请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return getInstance().exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------DELETE-------------------------------------------------------

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Class<T> responseType, Object... uriVariables) {
        return delete(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return delete(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        return delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        return delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return delete(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        return delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return delete(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        return delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return delete(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return delete(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的DELETE请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return getInstance().exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的DELETE请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return getInstance().exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------通用方法-------------------------------------------------------

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return getInstance().exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return getInstance().exchange(url, method, requestEntity, responseType, uriVariables);
    }

}
