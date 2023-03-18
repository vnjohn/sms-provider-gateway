package org.vnjohn.sms.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.enums.SMSCloudProviderEnum;

/**
 * sms.provider.service=TencentCloud 注入 Bean：TencentCloudSMSFactory、TencentCloudSMSServiceImpl
 *
 * @author vnjohn
 * @since 2023/3/17
 */
public class TencentCloudOnCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String propertyVal = context.getEnvironment().getProperty(Constants.SMS_CLOUD_PROVIDER_PROPERTIES);
        return null != propertyVal && propertyVal.equals(SMSCloudProviderEnum.TENCENT_CLOUD.getCode());
    }
}
