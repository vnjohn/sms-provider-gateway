package org.vnjohn.sms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author vnjohn
 * @since 2023/3/18
 */
@Data
@Component
@RefreshScope
public class SMSCloudProviderConfig {
    @Value("${sms.provider.ali.access-key}")
    private String aliAccessKey;

    @Value("${sms.provider.ali.secret}")
    private String aliSecret;

    @Value("${sms.provider.ali.endpoint:dysmsapi.aliyuncs.com}")
    private String aliEndPoint;

    @Value("${sms.provider.tencent.access-key}")
    private String tencentAccessKey;

    @Value("${sms.provider.tencent.secret}")
    private String tencentSecret;

    @Value("${sms.provider.tencent.endpoint:sms.tencentcloudapi.com}")
    private String tencentEndPoint;

    @Value("${sms.provider.tencent.region:ap-guangzhou}")
    private String tencentRegion;
}
