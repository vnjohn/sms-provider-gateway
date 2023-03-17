package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.PullSmsSendStatusByPhoneNumberRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.constants.Constants;
import org.vnjohn.sms.entity.AbstractSMSSendSms;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TencentStatusSendSms extends AbstractSMSSendSms {
    /**
     * 拉取起始时间，UNIX 时间戳（时间：秒）
     */
    private Long beginTime;

    /**
     * 拉取截止时间，UNIX 时间戳（时间：秒）
     */
    private Long endTime;

    /**
     * 短信 SdkAppId
     */
    private String smsSdkAppId;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 拉取最大条数，最多 100
     */
    private Integer limit;

    public PullSmsSendStatusByPhoneNumberRequest toPullSmsSendStatusByPhoneNumberRequest() {
        PullSmsSendStatusByPhoneNumberRequest sendStatusRequest = new PullSmsSendStatusByPhoneNumberRequest();
        sendStatusRequest.setPhoneNumber(getPhone());
        sendStatusRequest.setSmsSdkAppId(smsSdkAppId);
        sendStatusRequest.setBeginTime(beginTime);
        sendStatusRequest.setEndTime(endTime);
        // 分页在服务实现哪一块进行传入
        sendStatusRequest.setOffset(Constants.OFFSET.longValue());
        sendStatusRequest.setLimit(Constants.PULL_MAX_LIMIT.longValue());
        return sendStatusRequest;
    }
}
