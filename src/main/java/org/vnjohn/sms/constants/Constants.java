package org.vnjohn.sms.constants;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
public interface Constants {
    String COMMA = ",";
    String POUND_KEY = "#";
    String REDIS_CACHE_PREFIX = "vnjohn-sms:";
    String SMS_CLOUD_PROVIDER_PROPERTIES = "sms.provider.service";

    Integer OFFSET = 0;
    Integer PULL_MAX_LIMIT = 100;

    /**
     * 华为基础配置信息
     */
    String AUTH_IDENTIFY = "huawei-cloud.auth.identity";
    String AUTH_IDENTIFY_USER = "huawei-cloud.auth.identity.user";
    String AUTH_IDENTIFY_USER_DOMAIN = "huawei-cloud.auth.identity.user.domain";
    String AUTH_SCOPE_PROJECT = "huawei-cloud.auth.scope.project";

    /**
     * 华为认证 token URL
     */
    String HUAWEI_AUTH_URL = "huawei-cloud.auth.url";

    /**
     * 华为签名 URL
     */
    String HUAWEI_SIGN_QUERY_URL = "huawei-cloud.sign.query";

}
