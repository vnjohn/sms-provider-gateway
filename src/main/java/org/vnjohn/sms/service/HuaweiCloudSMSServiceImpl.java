package org.vnjohn.sms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.vnjohn.sms.condition.HuaweiCloudOnCondition;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
 * @author vnjohn
 * @since 2023/5/31
 */
@Slf4j
@Component
@Conditional(HuaweiCloudOnCondition.class)
public class HuaweiCloudSMSServiceImpl extends AbstractSMSService {

    /*public String authToken() {

    }*/

    @Override
    public <T extends AbstractSMSSign> String applySign(AbstractSMSSign applySmsSign) {
        return null;
    }

    @Override
    public <T extends AbstractSMSTemplate> String applyTemplate(AbstractSMSTemplate applySmsTemplate) {
        return null;
    }

    @Override
    public <T extends AbstractSMSSendSms> String sendSms(AbstractSMSSendSms sendSms) {
        return null;
    }

    @Override
    public void processMessageByCode(String code, String message) {

    }
}
