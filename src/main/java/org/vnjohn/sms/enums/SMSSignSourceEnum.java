package org.vnjohn.sms.enums;

import lombok.Getter;

/**
 * 签名来源，提供给外部调用的
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum SMSSignSourceEnum {
    /**
     * 私企、国企/事业单位、商标、网站、App应用、公众号、小程序
     */
    PRIVATE_ENTERPRISES(0, "私企"),
    GOVERNMENT(1, "国企/事业单位"),
    TRADEMARK(2, "商标"),
    WEBSITE(3, "网站"),
    APP(4, "App应用"),
    PUBLIC_ACCOUNT(5, "公众号"),
    APPLET(6, "小程序"),
    ELECTRIC_PLATFORM(7, "电商平台店铺名的全称或简称");

    private final Integer code;

    private final String desc;

    SMSSignSourceEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
