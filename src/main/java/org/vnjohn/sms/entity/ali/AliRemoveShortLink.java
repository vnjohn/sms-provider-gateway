package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.DeleteShortUrlRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSShortLink;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliRemoveShortLink extends AbstractSMSShortLink {

    public DeleteShortUrlRequest toDeleteShortUrlRequest() {
        return new DeleteShortUrlRequest().setSourceUrl(getSourceUrl());
    }
}
