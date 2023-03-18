package org.vnjohn.sms.dto;

import lombok.Data;
import org.vnjohn.sms.enums.SMSSmsTypeEnum;
import org.vnjohn.sms.enums.SMSTemplateTypeEnum;

import java.io.Serializable;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
public class ModifyTemplateDTO implements Serializable {
    private static final long serialVersionUID = -8386505703358311331L;

    /**
     * 模版id或编码
     */
    private String code;

    /**
     * 模版类型
     * {@link SMSTemplateTypeEnum}
     */
    private Integer type;

    /**
     * 短信类型
     * {@link SMSSmsTypeEnum}
     */
    private Integer smsType;

    /**
     * 模版名称
     */
    private String name;

    /**
     * 模版内容
     */
    private String content;

    /**
     * 申请原因，使用场景
     */
    private String remark;
}
