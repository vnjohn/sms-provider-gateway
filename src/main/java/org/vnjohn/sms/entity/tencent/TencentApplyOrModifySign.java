package org.vnjohn.sms.entity.tencent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tencentcloudapi.sms.v20210111.models.AddSmsSignRequest;
import com.tencentcloudapi.sms.v20210111.models.ModifySmsSignRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSign;

/**
 * 详见官方文档：https://cloud.tencent.com/document/product/382/55971
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentApplyOrModifySign extends AbstractSMSSign {
    /**
     * 签名 Id（申请完以后会返回）
     */
    private Long id;

    /**
     * 签名类型
     */
    private Integer source;

    /**
     *  证明类型
     */
    private Integer documentType;

    /**
     * 是否国际/港澳台短信：0：表示国内短信、1：表示国际/港澳台短信
     */
    private Integer type;

    /**
     * 签名用途：0：自用、1：他用
     */
    private Integer purpose;

    /**
     * 资质证明图片
     */
    @JsonIgnore
    private String proofImage;

    /**
     * 委托授权证明图，签名用途为 1 时需要
     */
    @JsonIgnore
    private String commissionImage;

    /**
     * 签名备注（可选）
     */
    private String remark;

    public AddSmsSignRequest toAddSmsSignRequest() {
        AddSmsSignRequest applySignRequest = new AddSmsSignRequest();
        applySignRequest.setSignName(getName());
        applySignRequest.setSignType(source.longValue());
        applySignRequest.setDocumentType(documentType.longValue());
        applySignRequest.setInternational(type.longValue());
        applySignRequest.setSignPurpose(purpose.longValue());
        applySignRequest.setProofImage(proofImage);
        applySignRequest.setCommissionImage(commissionImage);
        applySignRequest.setRemark(remark);
        return applySignRequest;
    }

    public ModifySmsSignRequest toModifySmsSignRequest() {
        ModifySmsSignRequest modifySignRequest = new ModifySmsSignRequest();
        modifySignRequest.setSignId(id);
        modifySignRequest.setSignName(getName());
        modifySignRequest.setSignType(source.longValue());
        modifySignRequest.setDocumentType(documentType.longValue());
        modifySignRequest.setInternational(type.longValue());
        modifySignRequest.setSignPurpose(purpose.longValue());
        modifySignRequest.setProofImage(proofImage);
        modifySignRequest.setCommissionImage(commissionImage);
        modifySignRequest.setRemark(remark);
        return modifySignRequest;
    }
}