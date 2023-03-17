package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.AddSmsTemplateRequest;
import com.aliyun.dysmsapi20170525.models.ModifySmsTemplateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
 * 详见官方文档：
 * 1、https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/AddSmsTemplate
 * 2、https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/ModifySmsTemplate
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliApplyOrModifyTemplate extends AbstractSMSTemplate {
    /**
     * 短信类型
     */
    private Integer type;

    /**
     * 模版名称，长度不超过30个字符
     */
    private String name;

    /**
     * 模版内容，长度不超过500个字符
     */
    private String content;

    /**
     * 短信模版申请说明，是模板审核的参考信息之一
     */
    private String remark;

    public AddSmsTemplateRequest toAddSmsTemplateRequest() {
        return new AddSmsTemplateRequest().setTemplateType(type)
                                          .setTemplateName(name)
                                          .setTemplateContent(content)
                                          .setRemark(remark);
    }

    public ModifySmsTemplateRequest toModifySmsTemplateRequest() {
        return new ModifySmsTemplateRequest().setTemplateType(type)
                                             .setTemplateName(name)
                                             .setTemplateCode(this.getCode())
                                             .setTemplateContent(content)
                                             .setRemark(remark);
    }
}
