package org.vnjohn.sms.enums.tencent;

import lombok.Getter;
import org.vnjohn.sms.enums.ali.AliSignTypeEnum;

import java.util.Arrays;

/**
 * 腾讯签名类型
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum TencentSignTypeEnum {
    /**
     * 国内短信、国际/港澳台短信
     */
    TERRITORY(0, "国内短信"),
    OVERSEAS(1, "国际/港澳台短信");
    private final Integer code;

    private final String desc;

    TencentSignTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TencentSignTypeEnum parseByCode(Integer code) {
        return Arrays.stream(TencentSignTypeEnum.values()).filter(signTypeEnum-> signTypeEnum.code.equals(code)).findFirst().orElse(null);
    }

}
