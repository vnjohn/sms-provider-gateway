package org.vnjohn.sms.request;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vnjohn
 * @since 2023/5/31
 */
@Data
public class BaseRequest {
    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";

    private HttpMethod method;

    private String url;

    private Map<String, String> headers = new HashMap<>();

    public void buildCommonHeader(String authToken) {
        headers.put(AUTH_TOKEN_HEADER, authToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
    }

}
