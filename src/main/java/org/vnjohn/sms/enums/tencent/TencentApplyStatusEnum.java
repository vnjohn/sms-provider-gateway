package org.vnjohn.sms.enums.tencent;

import lombok.Getter;
import org.vnjohn.sms.enums.SMSSignApplyStatusEnum;

import java.util.Arrays;

/**
 * @author vnjohn
 * @since 2023/3/18
 */
@Getter
public enum TencentApplyStatusEnum {
    /**
     * 审核中、审核通过、审核失败
     * {@link SMSSignApplyStatusEnum}
     */
    PENDING(0, 1, "审核中"),
    PASS(1, 0,"审核通过"),
    FAIL(2, -1, "审核失败");

    private final Integer innerCode;

    private final Integer outCode;

    private final String desc;

    TencentApplyStatusEnum(Integer innerCode, Integer outCode, String desc) {
        this.innerCode = innerCode;
        this.outCode = outCode;
        this.desc = desc;
    }

    public static TencentApplyStatusEnum parseByCode(Integer outCode) {
        return Arrays.stream(TencentApplyStatusEnum.values()).filter(applyStatusEnum -> applyStatusEnum.outCode.equals(outCode)).findFirst().orElse(null);
    }
}
