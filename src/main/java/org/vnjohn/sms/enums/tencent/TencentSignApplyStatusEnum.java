package org.vnjohn.sms.enums.tencent;

import lombok.Getter;
import org.vnjohn.sms.enums.SMSSignApplyStatusEnum;

import java.util.Arrays;

/**
 * @author vnjohn
 * @since 2023/3/18
 */
@Getter
public enum TencentSignApplyStatusEnum {
    /**
     * 审核中、审核通过、审核失败
     * {@link SMSSignApplyStatusEnum}
     */
    PENDING(0, 1, "审核中"),
    PASS(1, 0,"审核通过"),
    FAIL(2, -1, "审核失败");

    private final Integer innerCode;

    private final Integer code;

    private final String desc;

    TencentSignApplyStatusEnum(Integer innerCode, Integer code, String desc) {
        this.innerCode = innerCode;
        this.code = code;
        this.desc = desc;
    }

    public static TencentSignApplyStatusEnum parseByCode(Integer code) {
        return Arrays.stream(TencentSignApplyStatusEnum.values()).filter(applyStatusEnum -> applyStatusEnum.code.equals(code)).findFirst().orElse(null);
    }
}
