package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.DeleteSmsTemplateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentRemoveTemplate extends AbstractSMSTemplate {

    public DeleteSmsTemplateRequest toDeleteSmsTemplateRequest() {
        DeleteSmsTemplateRequest deleteTemplateRequest = new DeleteSmsTemplateRequest();
        deleteTemplateRequest.setTemplateId(Long.parseLong(getCode()));
        return deleteTemplateRequest;
    }

}