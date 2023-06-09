package org.vnjohn.sms.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.vnjohn.sms.condition.AliCloudOnCondition;
import org.vnjohn.sms.dto.*;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSShortLink;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.entity.ali.*;
import org.vnjohn.sms.enums.SMSTemplateTypeEnum;
import org.vnjohn.sms.enums.ali.AliSignSourceEnum;
import org.vnjohn.sms.enums.ali.AliSignTypeEnum;
import org.vnjohn.sms.utils.CertificationFileUtil;
import org.vnjohn.sms.utils.JacksonUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.vnjohn.sms.SmsBusinessException.throwNull;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Component
@Conditional(AliCloudOnCondition.class)
public class AliCloudSMSFactory extends AbstractSMSFactory {
    private static final String FILE_SUFFIX = "FileSuffix";
    private static final String FILE_CONTENT = "FileContents";

    @Override
    public <T extends AbstractSMSSign> AliApplyOrModifySign createApplySign(ApplySignDTO applySignDTO) {
        return getAbstractSMSSign(applySignDTO.getSource(), applySignDTO.getType(), applySignDTO.getCertificationImaFile(), applySignDTO.getName(), applySignDTO.getRemark());
    }

    @Override
    public <T extends AbstractSMSSign> AliApplyOrModifySign createModifySign(ModifySignDTO modifySignDTO) {
        return getAbstractSMSSign(modifySignDTO.getSource(), modifySignDTO.getType(), modifySignDTO.getCertificationImaFile(), modifySignDTO.getName(), modifySignDTO.getRemark());
    }

    private AliApplyOrModifySign getAbstractSMSSign(Integer source, Integer type, MultipartFile file, String name, String remark) {
        AliSignSourceEnum signSourceEnum = checkAliSignSource(source);
        AliSignTypeEnum signTypeEnum = checkAliSignType(type);
        String fileContent = processFileContent(file);
        return AliApplyOrModifySign.builder()
                                   .name(name)
                                   .source(signSourceEnum.getOutCode())
                                   .type(signTypeEnum.getCode())
                                   .fileList(fileContent)
                                   .remark(remark)
                                   .build();
    }

    @Override
    public <T extends AbstractSMSSign> AliRemoveSign createRemoveSign(Long signId, String signName) {
        return AliRemoveSign.builder().name(signName).build();
    }

    @Override
    public <T extends AbstractSMSSign> AliStatusSign createQuerySignStatus(Long signId, String signName, Integer type) {
        return AliStatusSign.builder().name(signName).build();
    }

    @Override
    public <T extends AbstractSMSTemplate> AliApplyOrModifyTemplate createApplyTemplate(ApplyTemplateDTO applyTemplateDTO) {
        SMSTemplateTypeEnum templateTypeEnum = checkTemplateType(applyTemplateDTO.getType());
        return AliApplyOrModifyTemplate.builder()
                                       .name(applyTemplateDTO.getName())
                                       .type(templateTypeEnum.getCode())
                                       .content(applyTemplateDTO.getContent())
                                       .remark(applyTemplateDTO.getRemark())
                                       .build();
    }

    @Override
    public <T extends AbstractSMSTemplate> AliApplyOrModifyTemplate createModifyTemplate(ModifyTemplateDTO modifyTemplateDTO) {
        SMSTemplateTypeEnum templateTypeEnum = checkTemplateType(modifyTemplateDTO.getType());
        return AliApplyOrModifyTemplate.builder()
                                       .code(modifyTemplateDTO.getCode())
                                       .name(modifyTemplateDTO.getName())
                                       .type(templateTypeEnum.getCode())
                                       .content(modifyTemplateDTO.getContent())
                                       .remark(modifyTemplateDTO.getRemark())
                                       .build();
    }

    @Override
    public <T extends AbstractSMSTemplate> AliRemoveTemplate createRemoveTemplate(String code) {
        return AliRemoveTemplate.builder().code(code).build();
    }

    @Override
    public <T extends AbstractSMSTemplate> AliStatusTemplate createQueryTemplateStatus(Integer type, String code) {
        return AliStatusTemplate.builder().code(code).build();
    }

    @Override
    public <T extends AbstractSMSSendSms> AbstractSMSSendSms createSendSms(SendSmsDTO sendSmsDTO) {
        return null;
    }

    @Override
    public <T extends AbstractSMSShortLink> String createShortLink() {
        return null;
    }

    @NotNull
    private AliSignTypeEnum checkAliSignType(Integer signType) {
        AliSignTypeEnum signTypeEnum = AliSignTypeEnum.parseByCode(signType);
        throwNull(signTypeEnum, "签名类型不存在");
        return signTypeEnum;
    }

    @NotNull
    private AliSignSourceEnum checkAliSignSource(Integer signSource) {
        AliSignSourceEnum signSourceEnum = AliSignSourceEnum.parseByInnerCode(signSource);
        throwNull(signSourceEnum, "签名来源不存在");
        return signSourceEnum;
    }

    @NotNull
    private SMSTemplateTypeEnum checkTemplateType(Integer type) {
        SMSTemplateTypeEnum templateTypeEnum = SMSTemplateTypeEnum.parseByCode(type);
        throwNull(templateTypeEnum, "模版类型不存在");
        return templateTypeEnum;
    }

    /**
     * 处理文件内容
     *
     * @param file
     * @return
     */
    private String processFileContent(MultipartFile file) {
        String fileSuffix = CertificationFileUtil.getFileSuffix(file);
        String fileContent = CertificationFileUtil.encryptFileToBase64(file);
        Map<String, String> fileContentMap = new HashMap<>(2);
        fileContentMap.put(FILE_SUFFIX, fileSuffix);
        fileContentMap.put(FILE_CONTENT, fileContent);
        return JacksonUtils.toJson(Collections.singleton(fileContentMap));
    }
}
