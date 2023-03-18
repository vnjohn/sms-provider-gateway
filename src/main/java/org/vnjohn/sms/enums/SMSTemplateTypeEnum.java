package org.vnjohn.sms.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 模版类型，提供给外部调用的
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSTemplateTypeEnum {
    /**
     * 验证码、短信通知、推广短信、国际/港澳台消息
     */
    VERIFICATION_CODE(0, "验证码"),
    NOTICE(1, "短信通知"),
    PROMOTE(2, "推广短信"),
    INTERNATIONAL(3, "国际/港澳台消息");

    private final Integer code;

    private final String desc;

    SMSTemplateTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SMSTemplateTypeEnum parseByCode(Integer code) {
        return Arrays.stream(SMSTemplateTypeEnum.values()).filter(smsTypeEnum-> smsTypeEnum.code.equals(code)).findFirst().orElse(null);
    }

}
