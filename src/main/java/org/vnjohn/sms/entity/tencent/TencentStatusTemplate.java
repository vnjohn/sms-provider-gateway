package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.DescribeSmsTemplateListRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSign;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentStatusTemplate extends AbstractSMSSign {
    /**
     * 模版ID（申请后会返回）
     */
    private Long id;

    /**
     * 是否国际/港澳台短信：0：表示国内短信、1：表示国际/港澳台短信
     */
    private Integer international;

    /**
     * 最大上限，最多100
     */
    private Integer limit;

    /**
     * 偏移量
     */
    private Integer offset;

    public DescribeSmsTemplateListRequest toDeleteSmsTemplateRequest() {
        DescribeSmsTemplateListRequest statusTemplateRequest = new DescribeSmsTemplateListRequest();
        statusTemplateRequest.setTemplateIdSet(new Long[]{id});
        statusTemplateRequest.setInternational(international.longValue());
        return statusTemplateRequest;
    }

}