package org.vnjohn.sms.enums.tencent;

import lombok.Getter;
import org.vnjohn.sms.enums.SMSTemplateTypeEnum;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 腾讯签名、模版类型
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum TencentSMSTypeEnum {
    /**
     * 国内短信、国际/港澳台短信
     * innerCode：模版类型，{@link SMSTemplateTypeEnum}
     */
    TERRITORY("^([0]|[1]|[2])$",0, "国内短信"),
    OVERSEAS("^([3])$", 1, "国际/港澳台短信");

    private final String innerCode;

    private final Integer outCode;

    private final String desc;

    TencentSMSTypeEnum(String innerCode, Integer outCode, String desc) {
        this.innerCode = innerCode;
        this.outCode = outCode;
        this.desc = desc;
    }

    public static TencentSMSTypeEnum parseByOutCode(Integer outCode) {
        return Arrays.stream(TencentSMSTypeEnum.values()).filter(signTypeEnum-> signTypeEnum.outCode.equals(outCode)).findFirst().orElse(null);
    }

    public static TencentSMSTypeEnum parseByInnerCode(Integer innerCode) {
        return Arrays.stream(TencentSMSTypeEnum.values()).filter(signTypeEnum-> Pattern.matches(signTypeEnum.innerCode, innerCode.toString())).findFirst().orElse(null);
    }

}
