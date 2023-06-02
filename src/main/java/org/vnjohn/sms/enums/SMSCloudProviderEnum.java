package org.vnjohn.sms.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 短信云提供商枚举类
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSCloudProviderEnum {
    /**
     * 阿里云、腾讯云、华为云、其他云.
     */
    ALI_CLOUD("AliCloud", "阿里云"),
    TENCENT_CLOUD("TencentCloud", "腾讯云"),
    HUAWEI_CLOUD("HuaweiCloud", "华为云");

    private final String code;
    private final String desc;

    SMSCloudProviderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SMSCloudProviderEnum parseByCode(String code) {
        return Arrays.stream(SMSCloudProviderEnum.values()).filter(smsCloudProviderEnum -> smsCloudProviderEnum.code.equals(code)).findFirst().orElse(null);
    }

}
