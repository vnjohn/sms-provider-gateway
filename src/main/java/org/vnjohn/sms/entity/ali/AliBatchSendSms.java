package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.utils.JacksonUtils;
import org.vnjohn.sms.utils.SmsTemplateContentParser;

import java.util.*;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliBatchSendSms extends AbstractSMSSendSms {
    /**
     * 下发手机号：验证码类型短信，建议使用接口 SendSms 单独发送
     */
    private Set<String> phoneNumberSet;

    /**
     * 短信签名：短信签名的个数必须与手机号码的个数相同、内容一一对应
     */
    private String signName;

    /**
     * 短信内容
     */
    private String smsContent;

    /**
     * 短信模版 Code
     */
    private String templateCode;

    /**
     * 短信模版参数：模板变量值的个数必须与手机号码、签名的个数相同、内容一一对应
     */
    private List<String> templateParamSet;

    public String getSignNameJson() {
        List<String> signNameList = new ArrayList<>(phoneNumberSet.size());
        for (int i = 0; i < phoneNumberSet.size(); i++) {
            signNameList.add(signName);
        }
        return JacksonUtils.toJson(signNameList);
    }

    public String getTemplateParamJson() {
        List<Map<String, String>> allPhoneMap = new ArrayList<>(phoneNumberSet.size());
        Map<String, String> paramValueMap = SmsTemplateContentParser.parseAliSmsContextParams(smsContent, templateParamSet);
        for (int i = 0; i < phoneNumberSet.size(); i++) {
            allPhoneMap.add(paramValueMap);
        }
        return JacksonUtils.toJson(allPhoneMap);
    }

    public SendBatchSmsRequest toSendBatchSmsRequest() {
        return new SendBatchSmsRequest()
                .setPhoneNumberJson(JacksonUtils.toJson(phoneNumberSet))
                .setSignNameJson(getSignNameJson())
                .setTemplateCode(templateCode)
                .setTemplateParamJson(getTemplateParamJson());
    }

}
