package org.vnjohn.sms.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 签名审核状态，提供给外部调用的
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSSignApplyStatusEnum {
    /**
     * 审核中、审核通过、审核失败
     */
    PENDING(0, "审核中"),
    PASS(1, "审核通过"),
    FAIL(2, "审核失败");

    private final Integer code;

    private final String desc;

    SMSSignApplyStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SMSSignApplyStatusEnum parseByCode(Integer code) {
        return Arrays.stream(SMSSignApplyStatusEnum.values()).filter(applyStatusEnum -> applyStatusEnum.code.equals(code)).findFirst().orElse(null);
    }

}
