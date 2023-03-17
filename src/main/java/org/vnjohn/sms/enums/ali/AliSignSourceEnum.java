package org.vnjohn.sms.enums.ali;

import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 阿里签名来源
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum AliSignSourceEnum {
    /**
     * 企事业单位的全称或简称、工信部备案网站的全称或简称、App应用的全称或简称、公众号或小程序的全称或简称、电商平台店铺名的全称或简称、商标名的全称或简称
     * 多个内部编码适配同一个阿里编码
     */
    ENTERPRISES("^([0]|[1])$", 0, "企事业单位的全称或简称"),
    WEBSITE("^([3])$", 1, "工信部备案网站的全称或简称"),
    APP("^([4])$", 2, "App应用的全称或简称"),
    PUBLIC_ACCOUNT("^([5]|[6])$", 3, "公众号或小程序的全称或简称"),
    ELECTRIC_PLATFORM("^([7])$", 4, "电商平台店铺名的全称或简称"),
    TRADEMARK("^([2])$", 5, "商标名的全称或简称");

    private final String innerCode;

    private final Integer outCode;

    private final String desc;

    AliSignSourceEnum(String innerCode, Integer outCode, String desc) {
        this.innerCode = innerCode;
        this.outCode = outCode;
        this.desc = desc;
    }

    public static AliSignSourceEnum parseByInnerCode(Integer innerCode) {
        return Arrays.stream(AliSignSourceEnum.values()).filter(signTypeEnum -> Pattern.matches(signTypeEnum.getInnerCode(), String.valueOf(innerCode))).findFirst().orElse(null);
    }

}
