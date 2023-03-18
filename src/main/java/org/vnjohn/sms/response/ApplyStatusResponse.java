package org.vnjohn.sms.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vnjohn.sms.enums.SMSSignApplyStatusEnum;

/**
 * @author vnjohn
 * @since 2023/3/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyStatusResponse {
    /**
     * 签名审核状态
     * {@link SMSSignApplyStatusEnum}
     */
    private Integer status;

    /**
     * 审核备注，失败的原因取它
     */
    private String reason;
}
