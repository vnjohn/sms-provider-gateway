package org.vnjohn.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.vnjohn.sms.SmsBusinessException;
import org.vnjohn.sms.condition.HuaweiCloudOnCondition;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.request.AuthTokenRequest;
import org.vnjohn.sms.utils.RestTemplateTools;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author vnjohn
 * @link {https://support.huaweicloud.com/api-msgsms/sms_05_0015.html}

 * POST https://iam.cn-north-1.myhuaweicloud.com/v3/auth/tokens
 * Content-Type: application/json
  {
      "auth": {
         "identity": {
              "methods": [
                  "password"
             ],
              "password": {
                 "user": {
                     "name": "xx",
                     "password": "xx",
                     "domain": {
                         "name": "xx"
                     }
                 }
            }
         },
         "scope": {
              "project": {
                "name": "cn-north-4	"
             }
         }
      }
 }
 * @author vnjohn
 * @since 2023/5/31
 */
@Data
@Component
@Conditional(HuaweiCloudOnCondition.class)
@Import({HuaweiAuthTokenParamAutoConfiguration.Auth.class,
        HuaweiAuthTokenParamAutoConfiguration.AuthIdentity.class,
        HuaweiAuthTokenParamAutoConfiguration.AuthIdentityPassword.class,
        HuaweiAuthTokenParamAutoConfiguration.AuthIdentityUser.class,
        HuaweiAuthTokenParamAutoConfiguration.AuthIdentityDomain.class,
        HuaweiAuthTokenParamAutoConfiguration.AuthScope.class,
        HuaweiAuthTokenParamAutoConfiguration.AuthScopeProject.class})
public class HuaweiAuthTokenParamAutoConfiguration {
    private static final String X_TOKEN = "X-Subject-Token";

    @Resource
    private Auth auth;

    @Data
    public static class Auth {
        @Resource
        private AuthIdentity identity;

        @Resource
        private AuthScope scope;
    }

    @Data
    @ConfigurationProperties(prefix = Constants.AUTH_IDENTIFY)
    public static class AuthIdentity {
        private List<String> methods;
        @Resource
        private AuthIdentityPassword password;
    }

    @Data
    public static class AuthIdentityPassword {
        @Resource
        private AuthIdentityUser user;
    }

    @Data
    @ConfigurationProperties(prefix = Constants.AUTH_IDENTIFY_USER)
    public static class AuthIdentityUser {
        private String name;
        private String password;
        @Resource
        private AuthIdentityDomain domain;
    }

    @Data
    @ConfigurationProperties(prefix = Constants.AUTH_IDENTIFY_USER_DOMAIN)
    public static class AuthIdentityDomain {
        private String name;
    }

    @Data
    public static class AuthScope {
        @Resource
        private AuthScopeProject project;
    }

    @Data
    @ConfigurationProperties(prefix = Constants.AUTH_SCOPE_PROJECT)
    public static class AuthScopeProject {
        private String name;
        private String id;
    }

    /**
     * 认证访问令牌 > 用于未提供 SDK 短信服务商，存入缓存 session 本地缓存一天
     *
     * @return 令牌
     */
    @Cacheable(value = "huawei-access-token#1d")
    public String authAccessToken() {
        // 优先读取缓存后 > 存入缓存 > 引入 Spring Session
        AuthTokenRequest tokenRequest = new AuthTokenRequest();
        ResponseEntity<Void> voidResponseEntity = RestTemplateTools.post(tokenRequest.getUrl(), this, Void.class);
        List<String> tokenHeaders = voidResponseEntity.getHeaders().get(X_TOKEN);
        if (CollectionUtils.isEmpty(tokenHeaders)) {
            throw new SmsBusinessException("Auth X-Subject-Token fail");
        }
        return tokenHeaders.get(0);
    }
}
