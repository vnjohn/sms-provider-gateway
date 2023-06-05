package org.vnjohn.sms.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpMethod;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.utils.GlobalSpringApplicationContext;

/**
 * @author vnjohn
 * @since 2023/6/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuerySignRequest extends BaseRequest { 
    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getUrl() {
        return GlobalSpringApplicationContext.getEnvProperties(Constants.HUAWEI_SIGN_QUERY_URL);
    }
}
