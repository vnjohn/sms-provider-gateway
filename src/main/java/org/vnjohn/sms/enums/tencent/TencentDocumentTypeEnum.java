package org.vnjohn.sms.enums.tencent;

import lombok.Getter;
import lombok.ToString;
import org.vnjohn.sms.constants.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 腾讯证明类型枚举
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
@ToString
public enum TencentDocumentTypeEnum {
    /**
     * 三证合一、APP、组织机构代码、社会信用代码、应用后台管理截图、网站备案后台截图、小程序设置页面截图、商标注册书、小程序设置页面截图
     */
    THREE_IN_ONE(0,"三证合一"),
    BUSINESS_LICENSE(1, "APP"),
    ORGANIZATION_CERTIFICATE(2,  "组织机构代码证书"),
    SOCIAL_CREDIT_CERTIFICATE(3, "社会信用代码证书"),
    BACK_MANAGEMENT(4,  "应用后台管理截图（个人开发APP）"),
    BACK_WEBSITE_RECORDS(5,  "网站备案后台截图（个人开发网站）"),
    APPLET_SETTING(6,  "小程序设置页面截图（个人认证小程序）"),
    TRADEMARK_REGISTRATION(7,  "商标注册书"),
    PUBLIC_ACCOUNT_SETTING(8,  "小程序设置页面截图（个人认证小程序）");

    private final Integer code;

    private final String desc;

    TencentDocumentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TencentDocumentTypeEnum parseByCode(Integer code) {
        return Arrays.stream(TencentDocumentTypeEnum.values()).filter(documentTypeEnum-> documentTypeEnum.code.equals(code)).findFirst().orElse(null);
    }

    public static List<String> parseByCodes(String codes) {
        List<String> codeList = Arrays.asList(codes.split(Constants.COMMA));
        return Arrays.stream(TencentDocumentTypeEnum.values()).filter(documentTypeEnum-> codeList.contains(documentTypeEnum.code.toString())).map(TencentDocumentTypeEnum::getDesc).collect(Collectors.toList());
    }

}
