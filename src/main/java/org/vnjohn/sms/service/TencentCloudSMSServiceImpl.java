package org.vnjohn.sms.service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.vnjohn.sms.SMSCloudProviderConfig;
import org.vnjohn.sms.SmsBusinessException;
import org.vnjohn.sms.condition.TencentCloudOnCondition;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.entity.tencent.*;
import org.vnjohn.sms.enums.SMSSignApplyStatusEnum;
import org.vnjohn.sms.enums.tencent.TencentApplyStatusEnum;
import org.vnjohn.sms.enums.tencent.TencentCommonCodeEnum;
import org.vnjohn.sms.response.ApplyStatusResponse;
import org.vnjohn.sms.utils.JacksonUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.vnjohn.sms.SmsBusinessException.throwBusinessException;
import static org.vnjohn.sms.SmsBusinessException.throwNull;

/**
 * 应用管理：https://console.cloud.tencent.com/smsv2
 * 密钥：https://console.cloud.tencent.com/cam/capi
 * @author vnjohn
 * @since 2023/3/17
 */
@Slf4j
@Component
@Conditional(TencentCloudOnCondition.class)
public class TencentCloudSMSServiceImpl extends AbstractSMSService {
    private static String ACCESS_KEY;
    private static String SECRET;
    private static String END_POINT;
    private static String REGION;

    @Resource
    private SMSCloudProviderConfig smsProviderConfig;

    /**
     * 阿里云查询偏移量
     */
    public static final long QUERY_PAGE = 1L;

    /**
     * 阿里云查询最大值
     */
    public static final long QUERY_MAX_SIZE = 50L;


    @PostConstruct
    public void init() {
        ACCESS_KEY = smsProviderConfig.getTencentAccessKey();
        SECRET = smsProviderConfig.getTencentSecret();
        END_POINT = smsProviderConfig.getTencentEndPoint();
        REGION = smsProviderConfig.getTencentRegion();
    }

    /**
     * 调用腾讯云客户端-确保安全单例模式
     */
    private static final class SingletonClientHolder {
        static SmsClient SINGLETON_CLIENT = null;
        static {
            try {
                Credential cred = new Credential(ACCESS_KEY, SECRET);
                HttpProfile httpProfile = new HttpProfile();
                httpProfile.setEndpoint(END_POINT);
                // 实例化要请求产品的 client 对象,clientProfile 是可选的
                SINGLETON_CLIENT= new SmsClient(cred, REGION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SmsClient getInstance() {
        return SingletonClientHolder.SINGLETON_CLIENT;
    }

    @Override
    public <T extends AbstractSMSSign> String applySign(AbstractSMSSign applySmsSign) {
        TencentApplyOrModifySign applySign = (TencentApplyOrModifySign) applySmsSign;
        AddSmsSignRequest addSmsSignRequest = applySign.toAddSmsSignRequest();
        try {
            AddSmsSignResponse applySmsSignResponse = getInstance().AddSmsSign(addSmsSignRequest);
            log.info("apply tencent sign，request【{}】，response【{}】", JacksonUtils.toJson(addSmsSignRequest), JacksonUtils.toJson(applySmsSignResponse));
            return String.valueOf(applySmsSignResponse.getAddSignStatus().getSignId());
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent applySign sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent applySign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSSign> String modifySign(AbstractSMSSign modifySmsSign) {
        TencentApplyOrModifySign modifySign = (TencentApplyOrModifySign) modifySmsSign;
        ModifySmsSignRequest modifySmsSignRequest = modifySign.toModifySmsSignRequest();
        try {
            ModifySmsSignResponse modifySmsSignResponse = getInstance().ModifySmsSign(modifySmsSignRequest);
            log.info("modify tencent sign，request【{}】，response【{}】", JacksonUtils.toJson(modifySmsSignRequest), JacksonUtils.toJson(modifySmsSignResponse));
            return String.valueOf(modifySmsSignResponse.getModifySignStatus().getSignId());
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent modifySign sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent modifySign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSSign> String removeSign(AbstractSMSSign removeSmsSign) {
        TencentRemoveSign removeSign = (TencentRemoveSign) removeSmsSign;
        DeleteSmsSignRequest deleteSmsSignRequest = removeSign.toDeleteSmsSignRequest();
        try {
            DeleteSmsSignResponse deleteSmsSignResponse = getInstance().DeleteSmsSign(deleteSmsSignRequest);
            log.info("remove tencent sign，request【{}】，response【{}】", JacksonUtils.toJson(deleteSmsSignRequest), JacksonUtils.toJson(deleteSmsSignResponse));
            return String.valueOf(deleteSmsSignResponse.getDeleteSignStatus().getDeleteStatus());
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent removeSign sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent removeSign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSSign> ApplyStatusResponse querySignApplyStatus(AbstractSMSSign statusSmsSign) {
        TencentStatusSign statusSign = (TencentStatusSign) statusSmsSign;
        DescribeSmsSignListRequest smsSignListRequest = statusSign.toDescribeSmsSignListRequest();
        try {
            DescribeSmsSignListResponse smsSignListResponse = getInstance().DescribeSmsSignList(smsSignListRequest);
            log.info("status tencent sign，request【{}】，response【{}】", JacksonUtils.toJson(smsSignListRequest), JacksonUtils.toJson(smsSignListResponse));
            DescribeSignListStatus[] describeSignListStatusSet = smsSignListResponse.getDescribeSignListStatusSet();
            throwNull(describeSignListStatusSet, "status tencent sign is null");
            DescribeSignListStatus signListStatuses = describeSignListStatusSet[0];
            TencentApplyStatusEnum signApplyStatusEnum = TencentApplyStatusEnum.parseByCode(signListStatuses.getStatusCode().intValue());
            return ApplyStatusResponse.builder().status(SMSSignApplyStatusEnum.parseByCode(signApplyStatusEnum.getInnerCode()).getCode()).reason(signListStatuses.getReviewReply()).build();
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent querySignApplyStatus sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent querySignApplyStatus Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSTemplate> String applyTemplate(AbstractSMSTemplate applySmsTemplate) {
        TencentApplyOrModifyTemplate applyTemplate = (TencentApplyOrModifyTemplate) applySmsTemplate;
        AddSmsTemplateRequest ddSmsTemplateRequest = applyTemplate.toAddSmsTemplateRequest();
        try {
            AddSmsTemplateResponse addSmsTemplateResponse = getInstance().AddSmsTemplate(ddSmsTemplateRequest);
            log.info("apply tencent template，request【{}】，response【{}】", JacksonUtils.toJson(ddSmsTemplateRequest), JacksonUtils.toJson(addSmsTemplateResponse));
            AddTemplateStatus addTemplateStatus = addSmsTemplateResponse.getAddTemplateStatus();
            return addTemplateStatus.getTemplateId();
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent applyTemplate sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent applyTemplate Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSTemplate> String modifyTemplate(AbstractSMSTemplate modifySmsTemplate) {
        TencentApplyOrModifyTemplate modifyTemplate = (TencentApplyOrModifyTemplate) modifySmsTemplate;
        ModifySmsTemplateRequest modifySmsTemplateRequest = modifyTemplate.toModifySmsTemplateRequest();
        try {
            ModifySmsTemplateResponse modifySmsTemplateResponse = getInstance().ModifySmsTemplate(modifySmsTemplateRequest);
            log.info("modify tencent template，request【{}】，response【{}】", JacksonUtils.toJson(modifySmsTemplateRequest), JacksonUtils.toJson(modifySmsTemplateResponse));
            ModifyTemplateStatus modifyTemplateStatus = modifySmsTemplateResponse.getModifyTemplateStatus();
            return modifyTemplateStatus.getTemplateId().toString();
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent modifyTemplate sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent modifyTemplate Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSTemplate> String removeTemplate(AbstractSMSTemplate removeSmsTemplate) {
        TencentRemoveTemplate removeTemplate = (TencentRemoveTemplate) removeSmsTemplate;
        DeleteSmsTemplateRequest deleteSmsTemplateRequest = removeTemplate.toDeleteSmsTemplateRequest();
        try {
            DeleteSmsTemplateResponse deleteSmsTemplateResponse = getInstance().DeleteSmsTemplate(deleteSmsTemplateRequest);
            log.info("remove tencent template，request【{}】，response【{}】", JacksonUtils.toJson(deleteSmsTemplateRequest), JacksonUtils.toJson(deleteSmsTemplateResponse));
            DeleteTemplateStatus deleteTemplateStatus = deleteSmsTemplateResponse.getDeleteTemplateStatus();
            return deleteTemplateStatus.getDeleteStatus();
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent removeTemplate sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent removeTemplate Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSTemplate> ApplyStatusResponse queryTemplateApplyStatus(AbstractSMSTemplate statusSmsTemplate) {
        TencentStatusTemplate statusTemplate = (TencentStatusTemplate) statusSmsTemplate;
        DescribeSmsTemplateListRequest smsTemplateListRequest = statusTemplate.toDescribeSmsTemplateListRequest();
        try {
            DescribeSmsTemplateListResponse smsTemplateListResponse = getInstance().DescribeSmsTemplateList(smsTemplateListRequest);
            log.info("status tencent template，request【{}】，response【{}】", JacksonUtils.toJson(smsTemplateListRequest), JacksonUtils.toJson(smsTemplateListResponse));
            DescribeTemplateListStatus[] describeTemplateListStatusSet = smsTemplateListResponse.getDescribeTemplateStatusSet();
            throwNull(describeTemplateListStatusSet, "status tencent template is null");
            DescribeTemplateListStatus templateListStatuses = describeTemplateListStatusSet[0];
            TencentApplyStatusEnum signApplyStatusEnum = TencentApplyStatusEnum.parseByCode(templateListStatuses.getStatusCode().intValue());
            return ApplyStatusResponse.builder().status(SMSSignApplyStatusEnum.parseByCode(signApplyStatusEnum.getInnerCode()).getCode()).reason(templateListStatuses.getReviewReply()).build();
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent queryTemplateApplyStatus sdkException：{}", sdkException.getMessage());
            processMessageByCode(sdkException.getErrorCode());
        } catch (Exception e) {
            log.error("Tencent queryTemplateApplyStatus Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
        return null;
    }

    @Override
    public <T extends AbstractSMSSendSms> String sendSms(AbstractSMSSendSms sendSms) {
        return null;
    }

    /**
     * 处理调用结果返回状态码
     *
     * @param code
     */
    public void processMessageByCode(String code) {
        processMessageByCode(code, null);
    }

    @Override
    public void processMessageByCode(String code, String message) {
        TencentCommonCodeEnum tencentCommonCodeEnum = TencentCommonCodeEnum.parseByCode(code);
        String throwMessage = null == tencentCommonCodeEnum ? String.format("该异常码%s未匹配，请查看对应错误码文档：https://cloud.tencent.com/document/api/382/52075", code) : tencentCommonCodeEnum.getMessage();
        throwBusinessException(throwMessage);
    }

}
