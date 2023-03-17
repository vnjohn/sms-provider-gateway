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
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.tencent.TencentApplyOrModifySign;
import org.vnjohn.sms.entity.tencent.TencentRemoveSign;
import org.vnjohn.sms.entity.tencent.TencentStatusSign;
import org.vnjohn.sms.enums.SMSSignApplyStatusEnum;
import org.vnjohn.sms.enums.tencent.TencentSignApplyStatusEnum;
import org.vnjohn.sms.response.SignStatusResponse;
import org.vnjohn.sms.utils.JacksonUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.vnjohn.sms.SmsBusinessException.throwNull;

/**
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
                Credential cred = new Credential(SECRET, ACCESS_KEY);
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
            throw new SmsBusinessException(String.format("apply tencent sign fail %s", sdkException.getMessage()));
        } catch (Exception e) {
            log.error("Tencent applySign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
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
            throw new SmsBusinessException(String.format("modify tencent sign fail %s", sdkException.getMessage()));
        } catch (Exception e) {
            log.error("Tencent modifySign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
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
            throw new SmsBusinessException(String.format("remove tencent sign fail %s", sdkException.getMessage()));
        } catch (Exception e) {
            log.error("Tencent removeSign Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractSMSSign> SignStatusResponse querySignApplyStatus(AbstractSMSSign statusSmsSign) {
        TencentStatusSign statusSign = (TencentStatusSign) statusSmsSign;
        DescribeSmsSignListRequest smsSignListRequest = statusSign.toDescribeSmsSignListRequest();
        try {
            DescribeSmsSignListResponse smsSignListResponse = getInstance().DescribeSmsSignList(smsSignListRequest);
            log.info("status tencent sign，request【{}】，response【{}】", JacksonUtils.toJson(smsSignListRequest), JacksonUtils.toJson(smsSignListResponse));
            DescribeSignListStatus[] describeSignListStatusSet = smsSignListResponse.getDescribeSignListStatusSet();
            throwNull(describeSignListStatusSet, "status tencent sign is null");
            DescribeSignListStatus signListStatuses = describeSignListStatusSet[0];
            TencentSignApplyStatusEnum signApplyStatusEnum = TencentSignApplyStatusEnum.parseByCode(signListStatuses.getStatusCode().intValue());
            return SignStatusResponse.builder().status(SMSSignApplyStatusEnum.parseByCode(signApplyStatusEnum.getInnerCode()).getCode()).reason(signListStatuses.getReviewReply()).build();
        } catch (TencentCloudSDKException sdkException) {
            log.error("Tencent querySignApplyStatus sdkException：{}", sdkException.getMessage());
            throw new SmsBusinessException(String.format("status tencent sign fail %s", sdkException.getMessage()));
        } catch (Exception e) {
            log.error("Tencent querySignApplyStatus Exception：{}", e.getMessage());
            throw new SmsBusinessException(e.getMessage());
        }
    }
}
