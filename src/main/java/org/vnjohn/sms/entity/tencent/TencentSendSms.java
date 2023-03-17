package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import java.util.List;
import java.util.Set;

/**
 * 应用管理：https://console.cloud.tencent.com/smsv2
 * 密钥：https://console.cloud.tencent.com/cam/capi
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TencentSendSms extends AbstractSMSSendSms {
    /**
     * 下发手机号码，采用 E.164 标准，格式为+[国家或地区码][手机号]，单次请求最多支持200个手机号且要求全为境内手机号或全为境外手机号
     */
    private Set<String> phoneNumberSet;

    /**
     * 短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名
     */
    private String signName;

    /**
     * 短信 SdkAppId
     */
    private String smsSdkAppId;

    /**
     * 模板 ID，必须填写已审核通过的模板 ID
     */
    private String templateId;

    /**
     * 模板参数，若无模板参数，则设置为空、模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致
     */
    private List<String> templateParamSet;

    public SendSmsRequest toSendBatchSmsRequest() {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumberSet(phoneNumberSet.toArray(new String[0]));
        sendSmsRequest.setSmsSdkAppId(smsSdkAppId);
        sendSmsRequest.setSignName(signName);
        sendSmsRequest.setTemplateId(templateId);
        sendSmsRequest.setTemplateParamSet(templateParamSet.toArray(new String[0]));
        return sendSmsRequest;
    }
}
