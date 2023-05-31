package org.vnjohn.sms.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.enums.SMSCloudProviderEnum;

/**
 * sms.provider.service=HuaweiCloud 注入 Bean：HuaweiCloudSMSFactory、HuaweiCloudSMSServiceImpl
 *
 * @author vnjohn
 * @since 2023/3/17
 */
public class HuaweiCloudOnCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String propertyVal = context.getEnvironment().getProperty(Constants.SMS_CLOUD_PROVIDER_PROPERTIES);
        return null != propertyVal && propertyVal.equals(SMSCloudProviderEnum.HUAWEI_CLOUD.getCode());
    }
}
