package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.DescribeSmsSignListRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.enums.tencent.TencentSMSTypeEnum;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentStatusSign extends AbstractSMSSign {
    /**
     * 签名 Id（申请完以后会返回）
     */
    private Long id;

    /**
     * 是否国际/港澳台短信：0：表示国内短信、1：表示国际/港澳台短信
     * {@link TencentSMSTypeEnum}
     */
    private Integer international;

    public DescribeSmsSignListRequest toDescribeSmsSignListRequest() {
        DescribeSmsSignListRequest deleteSignRequest = new DescribeSmsSignListRequest();
        deleteSignRequest.setSignIdSet(new Long[]{id});
        deleteSignRequest.setInternational(international.longValue());
        return deleteSignRequest;
    }
}
