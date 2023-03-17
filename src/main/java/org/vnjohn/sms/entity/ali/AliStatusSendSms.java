package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.QuerySendDetailsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSendSms;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliStatusSendSms extends AbstractSMSSendSms {
    /**
     * 发送回执 Id
     */
    private String bizId;

    /**
     * 短信发送日期，支持查询最近30天的记录
     * 格式：yyyyMMdd
     * 例如：20181225
     */
    private String sendDate;

    public QuerySendDetailsRequest toQuerySendDetailsRequest() {
        return new QuerySendDetailsRequest()
                .setPhoneNumber(getPhone())
                .setBizId(bizId)
                .setSendDate(sendDate);
    }

}
