package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliStatusTemplate extends AbstractSMSTemplate {

    public QuerySmsTemplateRequest toDeleteSmsTemplateRequest() {
        return new QuerySmsTemplateRequest().setTemplateCode(this.getCode());
    }
}
