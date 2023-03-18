package org.vnjohn.sms.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.vnjohn.sms.condition.TencentCloudOnCondition;
import org.vnjohn.sms.dto.*;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSShortLink;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.entity.tencent.*;
import org.vnjohn.sms.enums.SMSSmsTypeEnum;
import org.vnjohn.sms.enums.tencent.TencentDocumentTypeEnum;
import org.vnjohn.sms.enums.tencent.TencentSignSourceEnum;
import org.vnjohn.sms.enums.tencent.TencentSMSTypeEnum;
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
    public <T extends AbstractSMSSign> TencentApplyOrModifySign createApplySign(ApplySignDTO applySignDTO) {
        TencentSMSTypeEnum signTypeEnum = checkTencentSignType(applySignDTO.getType());
        TencentDocumentTypeEnum certificationTypeEnum = checkCertificationType(applySignDTO.getCertificationType());
        TencentSignSourceEnum signSourceEnum = checkTencentSignSource(applySignDTO.getSource(), certificationTypeEnum);
        String imageBase64 = CertificationFileUtil.encryptFileToBase64(applySignDTO.getFile());
        return TencentApplyOrModifySign.builder()
                                       .name(applySignDTO.getName())
                                       .documentType(certificationTypeEnum.getCode())
                                       .purpose(applySignDTO.getPurpose())
                                       .type(signTypeEnum.getOutCode())
                                       .proofImage(imageBase64)
                                       .remark(applySignDTO.getRemark())
                                       .source(signSourceEnum.getOutCode())
                                       .build();
    }

    @Override
    public <T extends AbstractSMSSign> TencentApplyOrModifySign createModifySign(ModifySignDTO modifySignDTO) {
        TencentSMSTypeEnum signTypeEnum = checkTencentSignType(modifySignDTO.getType());
        TencentDocumentTypeEnum certificationTypeEnum = checkCertificationType(modifySignDTO.getCertificationType());
        TencentSignSourceEnum signSourceEnum = checkTencentSignSource(modifySignDTO.getSource(), certificationTypeEnum);
        String imageBase64 = CertificationFileUtil.encryptFileToBase64(modifySignDTO.getFile());
        return TencentApplyOrModifySign.builder()
                                       .id(modifySignDTO.getId())
                                       .name(modifySignDTO.getName())
                                       .documentType(certificationTypeEnum.getCode())
                                       .purpose(modifySignDTO.getPurpose())
                                       .type(signTypeEnum.getOutCode())
                                       .proofImage(imageBase64)
                                       .remark(modifySignDTO.getRemark())
                                       .source(signSourceEnum.getOutCode())
                                       .build();
    }

    @Override
    public <T extends AbstractSMSSign> TencentRemoveSign createRemoveSign(Long signId, String signName) {
        return TencentRemoveSign.builder().id(signId).name(signName).build();
    }

    @Override
    public <T extends AbstractSMSSign> TencentStatusSign createQuerySignStatus(Long signId, String signName, Integer type) {
        TencentSMSTypeEnum signTypeEnum = checkTencentSignType(type);
        return TencentStatusSign.builder().id(signId).name(signName).international(signTypeEnum.getOutCode()).build();
    }

    @Override
    public <T extends AbstractSMSTemplate> TencentApplyOrModifyTemplate createApplyTemplate(ApplyTemplateDTO applyTemplateDTO) {
        TencentSMSTypeEnum templateTypeEnum = checkTencentTemplateType(applyTemplateDTO.getType());
        SMSSmsTypeEnum smsTypeEnum = checkTencentSmsType(applyTemplateDTO.getSmsType());
        return TencentApplyOrModifyTemplate.builder()
                                           .name(applyTemplateDTO.getName())
                                           .content(applyTemplateDTO.getContent())
                                           .type(smsTypeEnum.getCode())
                                           .international(templateTypeEnum.getOutCode())
                                           .remark(applyTemplateDTO.getRemark())
                                           .build();
    }

    @Override
    public <T extends AbstractSMSTemplate> TencentApplyOrModifyTemplate createModifyTemplate(ModifyTemplateDTO modifyTemplateDTO) {
        TencentSMSTypeEnum templateTypeEnum = checkTencentTemplateType(modifyTemplateDTO.getType());
        SMSSmsTypeEnum smsTypeEnum = checkTencentSmsType(modifyTemplateDTO.getSmsType());
        return TencentApplyOrModifyTemplate.builder()
                                           .code(modifyTemplateDTO.getCode())
                                           .name(modifyTemplateDTO.getName())
                                           .content(modifyTemplateDTO.getContent())
                                           .type(smsTypeEnum.getCode())
                                           .international(templateTypeEnum.getOutCode())
                                           .remark(modifyTemplateDTO.getRemark())
                                           .build();
    }

    @Override
    public <T extends AbstractSMSTemplate> TencentRemoveTemplate createRemoveTemplate(String code) {
        return TencentRemoveTemplate.builder().code(code).build();
    }

    @Override
    public <T extends AbstractSMSTemplate> TencentStatusTemplate createQueryTemplateStatus(Integer type, String code) {
        TencentSMSTypeEnum smsTypeEnum = checkTencentTemplateType(type);
        return TencentStatusTemplate.builder().code(code).international(smsTypeEnum.getOutCode()).build();
    }

    @Override
    public <T extends AbstractSMSShortLink> String createShortLink() {
        return null;
    }

    @Override
    public <T extends AbstractSMSSendSms> AbstractSMSSendSms createSendSms(SendSmsDTO sendSmsDTO) {
        return null;
    }

    @NotNull
    private SMSSmsTypeEnum checkTencentSmsType(Integer smsType) {
        SMSSmsTypeEnum smsTypeEnum = SMSSmsTypeEnum.parseByCode(smsType);
        throwNull(smsTypeEnum, "短信类型不存在");
        return smsTypeEnum;
    }

    @NotNull
    private TencentSMSTypeEnum checkTencentTemplateType(Integer type) {
        TencentSMSTypeEnum templateTypeEnum = TencentSMSTypeEnum.parseByInnerCode(type);
        throwNull(templateTypeEnum, "模版类型不存在");
        return templateTypeEnum;
    }

    @NotNull
    private TencentSMSTypeEnum checkTencentSignType(Integer signType) {
        TencentSMSTypeEnum signTypeEnum = TencentSMSTypeEnum.parseByOutCode(signType);
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
