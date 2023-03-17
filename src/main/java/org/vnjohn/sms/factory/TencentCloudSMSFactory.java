package org.vnjohn.sms.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.vnjohn.sms.condition.TencentCloudOnCondition;
import org.vnjohn.sms.dto.ApplySignDTO;
import org.vnjohn.sms.dto.ApplyTemplateDTO;
import org.vnjohn.sms.dto.ModifySignDTO;
import org.vnjohn.sms.dto.ModifyTemplateDTO;
import org.vnjohn.sms.entity.AbstractSMSShortLink;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.entity.tencent.TencentApplyOrModifySign;
import org.vnjohn.sms.entity.tencent.TencentRemoveSign;
import org.vnjohn.sms.entity.tencent.TencentStatusSign;
import org.vnjohn.sms.enums.tencent.TencentDocumentTypeEnum;
import org.vnjohn.sms.enums.tencent.TencentSignSourceEnum;
import org.vnjohn.sms.enums.tencent.TencentSignTypeEnum;
import org.vnjohn.sms.utils.CertificationFileUtil;

import java.util.regex.Pattern;

import static org.vnjohn.sms.SmsBusinessException.throwBusinessException;
import static org.vnjohn.sms.SmsBusinessException.throwNull;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Component
@Conditional(TencentCloudOnCondition.class)
public class TencentCloudSMSFactory extends AbstractSMSFactory {

    @Override
    public <T extends AbstractSMSSign> AbstractSMSSign createApplySign(ApplySignDTO applySignDTO) {
        TencentSignTypeEnum signTypeEnum = checkTencentSignType(applySignDTO.getType());
        TencentDocumentTypeEnum certificationTypeEnum = checkCertificationType(applySignDTO.getCertificationType());
        TencentSignSourceEnum signSourceEnum = checkTencentSignSource(applySignDTO.getSource(), certificationTypeEnum);
        String imageBase64 = CertificationFileUtil.encryptFileToBase64(applySignDTO.getFile());
        return TencentApplyOrModifySign.builder()
                                       .name(applySignDTO.getName())
                                       .documentType(certificationTypeEnum.getCode())
                                       .purpose(applySignDTO.getPurpose())
                                       .type(signTypeEnum.getCode())
                                       .proofImage(imageBase64)
                                       .remark(applySignDTO.getRemark())
                                       .source(signSourceEnum.getOutCode())
                                       .build();
    }

    @Override
    public <T extends AbstractSMSSign> AbstractSMSSign createModifySign(ModifySignDTO modifySignDTO) {
        TencentSignTypeEnum signTypeEnum = checkTencentSignType(modifySignDTO.getType());
        TencentDocumentTypeEnum certificationTypeEnum = checkCertificationType(modifySignDTO.getCertificationType());
        TencentSignSourceEnum signSourceEnum = checkTencentSignSource(modifySignDTO.getSource(), certificationTypeEnum);
        String imageBase64 = CertificationFileUtil.encryptFileToBase64(modifySignDTO.getFile());
        return TencentApplyOrModifySign.builder()
                                       .id(modifySignDTO.getId())
                                       .name(modifySignDTO.getName())
                                       .documentType(certificationTypeEnum.getCode())
                                       .purpose(modifySignDTO.getPurpose())
                                       .type(signTypeEnum.getCode())
                                       .proofImage(imageBase64)
                                       .remark(modifySignDTO.getRemark())
                                       .source(signSourceEnum.getOutCode())
                                       .build();
    }

    @Override
    public <T extends AbstractSMSSign> AbstractSMSSign createRemoveSign(Long signId, String signName) {
        return TencentRemoveSign.builder().id(signId).name(signName).build();
    }

    @Override
    public <T extends AbstractSMSSign> AbstractSMSSign createQuerySignStatus(Long signId, String signName) {
        return TencentStatusSign.builder().id(signId).name(signName).build();
    }

    @Override
    public <T extends AbstractSMSTemplate> AbstractSMSSign createApplyTemplate(ApplyTemplateDTO applyTemplateDTO) {
        return super.createApplyTemplate(applyTemplateDTO);
    }

    @Override
    public <T extends AbstractSMSTemplate> AbstractSMSSign createModifyTemplate(ModifyTemplateDTO modifyTemplateDTO) {
        return super.createModifyTemplate(modifyTemplateDTO);
    }

    @Override
    public <T extends AbstractSMSTemplate> AbstractSMSSign createRemoveTemplate() {
        return super.createRemoveTemplate();
    }

    @Override
    public <T extends AbstractSMSTemplate> AbstractSMSSign createQueryTemplateStatus() {
        return super.createQueryTemplateStatus();
    }

    @Override
    public <T extends AbstractSMSShortLink> AbstractSMSSign createShortLink() {
        return super.createShortLink();
    }

    @NotNull
    private TencentSignTypeEnum checkTencentSignType(Integer signType) {
        TencentSignTypeEnum signTypeEnum = TencentSignTypeEnum.parseByCode(signType);
        throwNull(signTypeEnum, "签名类型不存在");
        return signTypeEnum;
    }

    @NotNull
    private TencentDocumentTypeEnum checkCertificationType(Integer certificationType) {
        TencentDocumentTypeEnum certificationTypeEnum = TencentDocumentTypeEnum.parseByCode(certificationType);
        throwNull(certificationTypeEnum, "证明类型不存在");
        return certificationTypeEnum;
    }

    @NotNull
    private TencentSignSourceEnum checkTencentSignSource(Integer signSource, TencentDocumentTypeEnum certificationTypeEnum) {
        TencentSignSourceEnum signSourceEnum = TencentSignSourceEnum.parseByInnerCode(signSource);
        throwNull(signSourceEnum, "签名来源不存在");
        // 通过正则表达式匹配
        boolean matches = Pattern.matches(signSourceEnum.getPattern(), certificationTypeEnum.getCode().toString());
        if (!matches) {
            String message = String.format("此来源【%s】只支持证明类型 %s", signSourceEnum.getDesc(), TencentDocumentTypeEnum.parseByCodes(signSourceEnum.getDocument()));
            throwBusinessException(message);
        }
        return signSourceEnum;
    }

}
