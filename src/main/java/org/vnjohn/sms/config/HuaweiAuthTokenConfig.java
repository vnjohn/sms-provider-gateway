package org.vnjohn.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.vnjohn.sms.condition.HuaweiCloudOnCondition;
import org.vnjohn.sms.constants.Constants;

import javax.annotation.Resource;
import java.util.List;

/**
 * @link {https://support.huaweicloud.com/api-msgsms/sms_05_0015.html}
 *
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
                     "name": "vnjohn.blog",
                     "password": "vnjohn.blog.com",
                     "domain": {
                         "name": "vnjohn"
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
public class HuaweiAuthTokenConfig {
    @Resource
    private Auth auth;

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    public static class Auth {
        @Resource
        private AuthIdentity identity;

        @Resource
        private AuthScope scope;
    }

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    @ConfigurationProperties(prefix = Constants.AUTH_IDENTIFY)
    public static class AuthIdentity {
        private List<String> methods;
        @Resource
        private AuthIdentityPassword password;
    }

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    public static class AuthIdentityPassword {
        @Resource
        private AuthIdentityUser user;
    }

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    @ConfigurationProperties(prefix = Constants.AUTH_IDENTIFY_USER)
    public static class AuthIdentityUser {
        private String name;
        private String password;
        @Resource
        private AuthIdentityDomain user;
    }

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    @ConfigurationProperties(prefix = Constants.AUTH_IDENTIFY_USER_DOMAIN)
    public static class AuthIdentityDomain {
        private String name;
    }

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    public static class AuthScope {
        @Resource
        private AuthScopeProject project;
    }

    @Data
    @Component
    @Conditional(HuaweiCloudOnCondition.class)
    @ConfigurationProperties(prefix = Constants.AUTH_SCOPE_PROJECT)
    public static class AuthScopeProject {
        private String name;
    }
}
