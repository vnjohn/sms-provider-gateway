package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.QueryShortUrlRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSShortLink;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliStatusShortLink extends AbstractSMSShortLink {
    /**
     * 短链服务地址
     */
    private String shortUrl;

    public QueryShortUrlRequest toQueryShortUrlRequest() {
        return new QueryShortUrlRequest().setShortUrl(shortUrl);
    }
}
