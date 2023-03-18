package org.vnjohn.sms.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.vnjohn.sms.SMSCloudProviderConfig;
import org.vnjohn.sms.SmsBusinessException;
import org.vnjohn.sms.condition.AliCloudOnCondition;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.entity.ali.*;
import org.vnjohn.sms.enums.ali.AliCommonCodeEnum;
import org.vnjohn.sms.response.ApplyStatusResponse;
import org.vnjohn.sms.utils.JacksonUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.vnjohn.sms.SmsBusinessException.throwBusinessException;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Slf4j
@Component
@Conditional(AliCloudOnCondition.class)
public class AliCloudSMSServiceImpl extends AbstractSMSService {
    private static String END_POINT;
    private static String ACCESS_KEY;
    private static String SECRET;

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
        ACCESS_KEY = smsProviderConfig.getAliAccessKey();
        SECRET = smsProviderConfig.getAliSecret();
        END_POINT = smsProviderConfig.getAliEndPoint();
    }

    /**
     * 调用阿里云客户端-确保安全单例模式
     */
    private static final class SingletonClientHolder {
        static Client SINGLETON_CLIENT = null;

        static {
            try {
                SINGLETON_CLIENT = new Client(new Config().setAccessKeyId(ACCESS_KEY).setAccessKeySecret(SECRET).setEndpoint(END_POINT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Client getInstance() {
        return SingletonClientHolder.SINGLETON_CLIENT;
    }

    @Override
    public <T extends AbstractSMSSign> String applySign(AbstractSMSSign applySmsSign) {
        AliApplyOrModifySign applySign = (AliApplyOrModifySign) applySmsSign;
        AddSmsSignRequest addSmsSignRequest = applySign.toApplySmsSignRequest();
        try {
            AddSmsSignResponse applySmsSignResponse = getInstance().addSmsSign(addSmsSignRequest);
            log.info("apply ali sign，request【{}】，response【{}】", JacksonUtils.toJson(addSmsSignRequest), JacksonUtils.toJson(applySmsSignResponse));
            processResultByCode(applySmsSignResponse.getBody().getCode(), applySmsSignResponse.getBody().getMessage());
            return applySmsSignResponse.getBody().getSignName();
        } catch (TeaException teaException) {
            log.error("Ali applySign teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("apply ali sign fail");
        } catch (Exception e) {
            log.error("Ali applySign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSSign> String modifySign(AbstractSMSSign modifySmsSign) {
        AliApplyOrModifySign modifySign = (AliApplyOrModifySign) modifySmsSign;
        ModifySmsSignRequest modifySmsSignRequest = modifySign.toModifySmsSignRequest();
        try {
            ModifySmsSignResponse modifySmsSignResponse = getInstance().modifySmsSign(modifySmsSignRequest);
            log.info("modify ali sign，request【{}】，response【{}】", JacksonUtils.toJson(modifySmsSignRequest), JacksonUtils.toJson(modifySmsSignResponse));
            processResultByCode(modifySmsSignResponse.getBody().getCode(), modifySmsSignResponse.getBody().getMessage());
            return modifySmsSignResponse.getBody().getSignName();
        } catch (TeaException teaException) {
            log.error("Ali modifySign teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("modify ali sign error");
        } catch (Exception e) {
            log.error("Ali modifySign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSSign> String removeSign(AbstractSMSSign removeSmsSign) {
        AliRemoveSign removeSign = (AliRemoveSign) removeSmsSign;
        DeleteSmsSignRequest deleteSmsSignRequest = new DeleteSmsSignRequest().setSignName(removeSign.getName());
        try {
            DeleteSmsSignResponse deleteSmsSignResponse = getInstance().deleteSmsSign(deleteSmsSignRequest);
            log.info("remove ali sign，request【{}】，response【{}】", JacksonUtils.toJson(deleteSmsSignRequest), JacksonUtils.toJson(deleteSmsSignResponse));
            processResultByCode(deleteSmsSignResponse.getBody().getCode(), deleteSmsSignResponse.getBody().getMessage());
            return deleteSmsSignResponse.getBody().getCode();
        } catch (TeaException teaException) {
            log.error("Ali removeSign teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("remove ali sign error");
        } catch (Exception e) {
            log.error("Ali removeSign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSSign> ApplyStatusResponse querySignApplyStatus(AbstractSMSSign statusSmsSign) {
        AliStatusSign statusSign = (AliStatusSign) statusSmsSign;
        QuerySmsSignRequest querySmsSignRequest = new QuerySmsSignRequest().setSignName(statusSign.getName());
        try {
            QuerySmsSignResponse querySmsSignResponse = getInstance().querySmsSign(querySmsSignRequest);
            log.info("query ali sign status，request【{}】，response【{}】", JacksonUtils.toJson(querySmsSignRequest), JacksonUtils.toJson(querySmsSignResponse));
            QuerySmsSignResponseBody body = querySmsSignResponse.getBody();
            processResultByCode(body.getCode(), body.getMessage());
            return ApplyStatusResponse.builder().status(body.getSignStatus()).reason(body.getReason()).build();
        } catch (TeaException teaException) {
            log.error("AliCloud querySmsSignStatus teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("query ali sign status error");
        } catch (Exception e) {
            log.error("AliCloud querySmsSignStatus Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSTemplate> String applyTemplate(AbstractSMSTemplate applySmsTemplate) {
        AliApplyOrModifyTemplate applyTemplate = (AliApplyOrModifyTemplate) applySmsTemplate;
        AddSmsTemplateRequest addSmsTemplateRequest = applyTemplate.toAddSmsTemplateRequest();
        try {
            AddSmsTemplateResponse addSmsTemplateResponse = getInstance().addSmsTemplate(addSmsTemplateRequest);
            log.info("apply ali template，request【{}】，response【{}】", JacksonUtils.toJson(addSmsTemplateRequest), JacksonUtils.toJson(addSmsTemplateResponse));
            processResultByCode(addSmsTemplateResponse.getBody().getCode(), addSmsTemplateResponse.getBody().getMessage());
            return addSmsTemplateResponse.getBody().getTemplateCode();
        } catch (TeaException teaException) {
            log.error("Ali applyTemplate teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("apply ali template error");
        } catch (Exception e) {
            log.error("Ali applyTemplate Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSTemplate> String modifyTemplate(AbstractSMSTemplate modifySmsTemplate) {
        AliApplyOrModifyTemplate applyTemplate = (AliApplyOrModifyTemplate) modifySmsTemplate;
        ModifySmsTemplateRequest modifySmsTemplateRequest = applyTemplate.toModifySmsTemplateRequest();
        try {
            ModifySmsTemplateResponse modifySmsTemplateResponse = getInstance().modifySmsTemplate(modifySmsTemplateRequest);
            log.info("modify ali template，request【{}】，response【{}】", JacksonUtils.toJson(modifySmsTemplateRequest), JacksonUtils.toJson(modifySmsTemplateResponse));
            processResultByCode(modifySmsTemplateResponse.getBody().getCode(), modifySmsTemplateResponse.getBody().getMessage());
            return modifySmsTemplateResponse.getBody().getTemplateCode();
        } catch (TeaException teaException) {
            log.error("Ali modifyTemplate teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("modify ali template error");
        } catch (Exception e) {
            log.error("Ali modifyTemplate Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSTemplate> String removeTemplate(AbstractSMSTemplate removeSmsTemplate) {
        AliRemoveTemplate removeTemplate = (AliRemoveTemplate) removeSmsTemplate;
        DeleteSmsTemplateRequest deleteSmsTemplateRequest = removeTemplate.toDeleteSmsTemplateRequest();
        try {
            DeleteSmsTemplateResponse deleteSmsTemplateResponse = getInstance().deleteSmsTemplate(deleteSmsTemplateRequest);
            log.info("remove ali template，request【{}】，response【{}】", JacksonUtils.toJson(deleteSmsTemplateRequest), JacksonUtils.toJson(deleteSmsTemplateResponse));
            processResultByCode(deleteSmsTemplateResponse.getBody().getCode(), deleteSmsTemplateResponse.getBody().getMessage());
            return deleteSmsTemplateResponse.getBody().getTemplateCode();
        } catch (TeaException teaException) {
            log.error("Ali removeTemplate teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("remove ali template error");
        } catch (Exception e) {
            log.error("Ali removeTemplate Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSTemplate> ApplyStatusResponse queryTemplateApplyStatus(AbstractSMSTemplate statusSmsTemplate) {
        AliStatusTemplate statusTemplate = (AliStatusTemplate) statusSmsTemplate;
        QuerySmsTemplateRequest querySmsTemplateRequest = new QuerySmsTemplateRequest().setTemplateCode(statusTemplate.getCode());
        try {
            QuerySmsTemplateResponse querySmsTemplateResponse = getInstance().querySmsTemplate(querySmsTemplateRequest);
            log.info("status ali template，request【{}】，response【{}】", JacksonUtils.toJson(querySmsTemplateRequest), JacksonUtils.toJson(querySmsTemplateResponse));
            QuerySmsTemplateResponseBody body = querySmsTemplateResponse.getBody();
            processResultByCode(body.getCode(), body.getMessage());
            return ApplyStatusResponse.builder().status(body.getTemplateStatus()).reason(body.getReason()).build();
        } catch (TeaException teaException) {
            log.error("Ali queryTemplateApplyStatus teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("status ali template error");
        } catch (Exception e) {
            log.error("Ali queryTemplateApplyStatus Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    /**
     * 处理调用结果返回状态码，message：API 返回的错误消息更详细，不存在，就需状态枚举错误消息
     */
    public void processResultByCode(String code, String message) {
        AliCommonCodeEnum aliErrorCodeEnum = AliCommonCodeEnum.parseByCode(code);
        if (aliErrorCodeEnum != null && aliErrorCodeEnum.getCode().equals(AliCommonCodeEnum.OK.getCode())) {
            return;
        }
        String throwMessage = StringUtils.isNotEmpty(message) ? message : null == aliErrorCodeEnum ? null : aliErrorCodeEnum.getMessage();
        throwBusinessException(throwMessage);
    }
}
