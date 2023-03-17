package org.vnjohn.sms.enums;

import lombok.Getter;

/**
 * 签名用途
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSSignPurposeEnum {
    /**
     * 自用、他用
     */
    PERSONAL_USE(0, "自用"),
    HE_USE(1, "他用");

    private final Integer code;

    private final String desc;

    SMSSignPurposeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
