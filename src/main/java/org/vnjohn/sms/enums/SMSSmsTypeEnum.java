package org.vnjohn.sms.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 短信类型，提供给外部调用的
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSSmsTypeEnum {
    /**
     * 普通短信、营销短信
     */
    ORDINARY(0, "普通短信"),
    MARKETING(1, "营销短信");

    private final Integer code;

    private final String desc;

    SMSSmsTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SMSSmsTypeEnum parseByCode(Integer code) {
        return Arrays.stream(SMSSmsTypeEnum.values()).filter(smsTypeEnum-> smsTypeEnum.code.equals(code)).findFirst().orElse(null);
    }

}
