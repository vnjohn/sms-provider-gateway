package org.vnjohn.sms.request;

import com.alibaba.nacos.common.utils.HttpMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.utils.GlobalSpringApplicationContext;

/**
 * @author vnjohn
 * @since 2023/5/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthTokenRequest extends BaseRequest {
    @Override
    public String getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getUrl() {
        return GlobalSpringApplicationContext.getEnvProperties(Constants.HUAWEI_AUTH_URL);
    }
}
