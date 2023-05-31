package org.vnjohn.sms.huawei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vnjohn.sms.config.HuaweiAuthTokenConfig;
import org.vnjohn.sms.request.AuthTokenRequest;
import org.vnjohn.sms.utils.JacksonUtils;


/**
 * @author vnjohn
 *
 * @since 2023/5/31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthTest {
    @Autowired(required = false)
    private HuaweiAuthTokenConfig authTokenConfig;

    @Autowired(required = false)
    private HuaweiAuthTokenConfig.AuthIdentity authTokenConfig1;

    @Test
    public void getAuthConfig() {
        System.out.println(JacksonUtils.toJson(authTokenConfig));
        System.out.println(JacksonUtils.toJson(authTokenConfig1));
        AuthTokenRequest authTokenRequest = new AuthTokenRequest();

        System.out.println(JacksonUtils.toJson(authTokenRequest));
    }
}
