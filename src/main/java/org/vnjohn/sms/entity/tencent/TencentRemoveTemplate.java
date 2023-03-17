package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.DeleteSmsTemplateRequest;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentRemoveTemplate extends AbstractSMSSign {
    /**
     * 模版ID（申请后会返回）
     */
    private Long id;

    public DeleteSmsTemplateRequest toDeleteSmsTemplateRequest() {
        DeleteSmsTemplateRequest deleteTemplateRequest = new DeleteSmsTemplateRequest();
        deleteTemplateRequest.setTemplateId(id);
        return deleteTemplateRequest;
    }

}