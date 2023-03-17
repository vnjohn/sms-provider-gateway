package org.vnjohn.sms.enums;

import lombok.Getter;

/**
 * 签名类型，提供给外部调用的
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSSignTypeEnum {
    /**
     * 验证码/国内、通用/境外
     */
    INNER(0, "验证码/国内"),
    OUTSIDE(1, "通用/境外");

    private final Integer code;

    private final String desc;

    SMSSignTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
