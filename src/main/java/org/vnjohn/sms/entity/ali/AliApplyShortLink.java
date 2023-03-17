package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.AddShortUrlRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSShortLink;

/**
 * 详见官方文档：https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/AddShortUrl
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliApplyShortLink extends AbstractSMSShortLink {
    /**
     * 短链服务名称
     */
    private String shortUrlName;

    /**
     * 使用有效期，单位：天，最长为 90 天
     */
    private String effectiveDays;

    public AddShortUrlRequest toAddShortUrlRequest() {
        return new AddShortUrlRequest().setSourceUrl(getSourceUrl())
                                     .setShortUrlName(shortUrlName)
                                     .setEffectiveDays(effectiveDays);
    }
}
