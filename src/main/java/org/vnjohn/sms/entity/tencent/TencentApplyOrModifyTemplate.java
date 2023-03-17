package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.AddSmsTemplateRequest;
import com.tencentcloudapi.sms.v20210111.models.ModifySmsTemplateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSign;

/**
 * 详见官方文档：https://cloud.tencent.com/document/product/382/55974
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentApplyOrModifyTemplate extends AbstractSMSSign {
    /**
     * 模版ID（申请后会返回）
     */
    private Long id;

    /**
     * 模版名称
     */
    private String name;

    /**
     * 模版内容
     */
    private String content;

    /**
     * 是否国际/港澳台短信：0：表示国内短信、1：表示国际/港澳台短信
     */
    private Integer international;

    /**
     * 短信类型：0-普通短信、1-营销短信
     */
    private Integer type;

    /**
     * 签名备注（可选）
     */
    private String remark;

    public AddSmsTemplateRequest toAddSmsSignRequest() {
        AddSmsTemplateRequest applyTemplateRequest = new AddSmsTemplateRequest();
        applyTemplateRequest.setTemplateName(getName());
        applyTemplateRequest.setTemplateContent(content);
        applyTemplateRequest.setSmsType(type.longValue());
        applyTemplateRequest.setInternational(international.longValue());
        applyTemplateRequest.setRemark(remark);
        return applyTemplateRequest;
    }

    public ModifySmsTemplateRequest toModifySmsSignResponse() {
        ModifySmsTemplateRequest modifyTemplateRequest = new ModifySmsTemplateRequest();
        modifyTemplateRequest.setTemplateId(id);
        modifyTemplateRequest.setTemplateName(getName());
        modifyTemplateRequest.setTemplateContent(content);
        modifyTemplateRequest.setSmsType(type.longValue());
        modifyTemplateRequest.setInternational(international.longValue());
        modifyTemplateRequest.setRemark(remark);
        return modifyTemplateRequest;
    }
}