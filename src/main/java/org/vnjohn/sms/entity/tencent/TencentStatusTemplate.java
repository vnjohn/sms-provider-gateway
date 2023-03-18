package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.DescribeSmsTemplateListRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
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
public class TencentStatusTemplate extends AbstractSMSTemplate {
    /**
     * 是否国际/港澳台短信：0：表示国内短信、1：表示国际/港澳台短信
     * {@link TencentSMSTypeEnum}
     */
    private Integer international;

    public DescribeSmsTemplateListRequest toDescribeSmsTemplateListRequest() {
        DescribeSmsTemplateListRequest statusTemplateRequest = new DescribeSmsTemplateListRequest();
        statusTemplateRequest.setTemplateIdSet(new Long[]{Long.parseLong(getCode())});
        statusTemplateRequest.setInternational(international.longValue());
        return statusTemplateRequest;
    }

}