package org.vnjohn.sms.huawei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.vnjohn.sms.config.HuaweiAuthTokenParamAutoConfiguration;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.request.QuerySignRequest;
import org.vnjohn.sms.response.huawei.QuerySignResponse;
import org.vnjohn.sms.service.AbstractSMSService;
import org.vnjohn.sms.utils.RestTemplateTools;

import javax.annotation.Resource;


/**
 * @author vnjohn
 *
 * @since 2023/5/31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private AbstractSMSService smsService;

    @Resource
    private HuaweiAuthTokenParamAutoConfiguration authTokenParamAutoConfiguration;

    @Test
    public void getAuthConfig() {
        System.out.println(applicationContext.getEnvironment().getProperty(Constants.SMS_CLOUD_PROVIDER_PROPERTIES));
        System.out.println(authTokenParamAutoConfiguration.authAccessToken());
    }

    @Test
    public void getSignConfig() {
        System.out.println(applicationContext.getEnvironment().getProperty(Constants.HUAWEI_SIGN_QUERY_URL));
        QuerySignRequest signRequest = new QuerySignRequest();
        signRequest.buildCommonHeader(authTokenParamAutoConfiguration.authAccessToken());
//        ResponseEntity<QuerySignResponse> querySignResponseResponseEntity = RestTemplateTools.get(String.format(signQueryUrl, authTokenParamAutoConfiguration.getAuth().getScope().getProject().getId()), QuerySignResponse.class);
        ResponseEntity<QuerySignResponse> response = (ResponseEntity<QuerySignResponse>) RestTemplateTools.request(signRequest, QuerySignResponse.class);
        System.out.println(response);
    }

}
