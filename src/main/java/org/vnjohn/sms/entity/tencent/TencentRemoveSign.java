package org.vnjohn.sms.entity.tencent;

import com.tencentcloudapi.sms.v20210111.models.DeleteSmsSignRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSign;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TencentRemoveSign extends AbstractSMSSign {
    /**
     * 签名 Id（申请完以后会返回）
     */
    private Long id;

    public DeleteSmsSignRequest toDeleteSmsSignRequest() {
        DeleteSmsSignRequest deleteSignRequest = new DeleteSmsSignRequest();
        deleteSignRequest.setSignId(id);
        return deleteSignRequest;
    }
}
