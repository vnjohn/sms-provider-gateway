package org.vnjohn.sms.enums.tencent;

import lombok.Getter;
import org.vnjohn.sms.constants.Constants;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 腾讯签名来源枚举
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum TencentSignSourceEnum {
    /**
     * 公司、APP、网站、公众号、商标、政府/相关事业单位/其他机构、小程序
     * 关于是否适配 DocumentType 类型，通过正则来进行匹配，较为快一些，document 用于返回匹配不成功的错误信息
     */
    COMPANY(0, 0, "公司", "^([0]|[1])$", "0,1"),
    APP(4, 1, "APP", "^([0]|[1]|[2]|[3]|[4])$", "0,1,2,3,4"),
    WEBSITE(3, 2, "网站", "^([0]|[1]|[2]|[3]|[5])$", "0,1,2,3,5"),
    PUBLIC_ACCOUNT(5, 3, "公众号", "^([0]|[1]|[2]|[3]|[8])$", "0,1,2,3,8"),
    TRADEMARK(2, 4, "商标", "^([7])$", "7"),
    GOVERNMENT(1, 5, "政府/机关事业单位/其他机构", "^([2]|[3])$", "2,3"),
    APPLET(6, 6, "小程序", "^([0]|[1]|[2]|[3]|[6])$", "0,1,2,3,6");

    private final Integer innerCode;

    private final Integer outCode;

    private final String desc;

    private final String pattern;

    private final String document;

    TencentSignSourceEnum(Integer innerCode, Integer outCode, String desc, String pattern, String document) {
        this.innerCode = innerCode;
        this.outCode = outCode;
        this.desc = desc;
        this.pattern = pattern;
        this.document = document;
    }

    public static TencentSignSourceEnum parseByInnerCode(Integer innerCode) {
        return Arrays.stream(TencentSignSourceEnum.values()).filter(signTypeEnum -> signTypeEnum.getInnerCode().equals(innerCode)).findFirst().orElse(null);
    }

    /**
     * 通过本地来模拟正则与普通遍历方式的快慢
     *
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        boolean matches = false;
        for (int i = 0; i < 100; i++) {
            matches = Pattern.matches(TencentSignSourceEnum.COMPANY.getPattern(), "0");
        }
        System.out.println("匹配时间：" + (System.currentTimeMillis() - startTime) + "，匹配结果：" + matches);
        for (int i = 0; i < 100; i++) {
            String[] split = TencentSignSourceEnum.COMPANY.getDocument().split(Constants.COMMA);
            matches = Arrays.asList(split).contains("0");
        }
        System.out.println("匹配时间：" + (System.currentTimeMillis() - startTime) + "，匹配结果：" + matches);
    }

}
