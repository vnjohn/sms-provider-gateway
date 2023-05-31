package org.vnjohn.sms.request;

import lombok.Data;

/**
 * @author vnjohn
 * @since 2023/5/31
 */
@Data
public class BaseRequest {
    private String method;

    private String url;
}
