package org.vnjohn.sms.enums.ali;

import lombok.Getter;

import java.util.Arrays;

/**
 * 阿里签名类型
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum AliSignTypeEnum {
    /**
     * 验证码、通用
     */
    VERIFICATION_CODE(0, "验证码"),
    GENERAL(1, "通用");
    private final Integer code;

    private final String desc;

    AliSignTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AliSignTypeEnum parseByCode(Integer code) {
        return Arrays.stream(AliSignTypeEnum.values()).filter(signTypeEnum-> signTypeEnum.code.equals(code)).findFirst().orElse(null);
    }
}
