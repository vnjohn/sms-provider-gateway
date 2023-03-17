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
import org.vnjohn.sms.entity.ali.AliApplyOrModifySign;
import org.vnjohn.sms.entity.ali.AliRemoveSign;
import org.vnjohn.sms.entity.ali.AliStatusSign;
import org.vnjohn.sms.enums.ali.AliCommonCodeEnum;
import org.vnjohn.sms.response.SignStatusResponse;
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
            log.info("create ali sign，request【{}】，response【{}】", JacksonUtils.toJson(addSmsSignRequest), JacksonUtils.toJson(applySmsSignResponse));
            processResultByCode(applySmsSignResponse.getBody().getCode(), applySmsSignResponse.getBody().getMessage());
            return applySmsSignResponse.getBody().getSignName();
        } catch (TeaException teaException) {
            log.error("Ali applySign teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("create ali sign fail");
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
    public <T extends AbstractSMSSign> SignStatusResponse querySignApplyStatus(AbstractSMSSign statusSmsSign) {
        AliStatusSign statusSign = (AliStatusSign) statusSmsSign;
        QuerySmsSignRequest querySmsSignRequest = new QuerySmsSignRequest().setSignName(statusSign.getName());
        try {
            QuerySmsSignResponse querySmsSignResponse = getInstance().querySmsSign(querySmsSignRequest);
            log.info("query ali sign status，request【{}】，response【{}】", JacksonUtils.toJson(querySmsSignRequest), JacksonUtils.toJson(querySmsSignResponse));
            QuerySmsSignResponseBody body = querySmsSignResponse.getBody();
            processResultByCode(querySmsSignResponse.getBody().getCode(), querySmsSignResponse.getBody().getMessage());
            return SignStatusResponse.builder().status(body.getSignStatus()).reason(body.getReason()).build();
        } catch (TeaException teaException) {
            log.error("AliCloud querySmsSignStatus teaException：{}", teaException.getMessage());
            throw new SmsBusinessException("query ali sign status error");
        } catch (Exception e) {
            log.error("AliCloud querySmsSignStatus Exception：{}", e.getMessage());
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
