package org.vnjohn.sms.entity.ali;

import com.aliyun.dysmsapi20170525.models.AddSmsSignRequest;
import com.aliyun.dysmsapi20170525.models.ModifySmsSignRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.enums.ali.AliSignSourceEnum;
import org.vnjohn.sms.enums.ali.AliSignTypeEnum;
import org.vnjohn.sms.utils.JacksonUtils;

import java.util.List;

/**
 * 详见官方文档：https://next.api.aliyun.com/document/Dysmsapi/2017-05-25/AddSmsSign
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliApplyOrModifySign extends AbstractSMSSign {
    /**
     * 签名来源
     * {@link AliSignSourceEnum}
     */
    private Integer source;

    /**
     * 短信签名申请说明
     */
    private String remark;

    /**
     * 签名文件列表
     * [{"FileContents":"","FileSuffix":""}]
     */
    private String fileList;

    /**
     * 签名类型：0：验证码、1：通用
     * {@link AliSignTypeEnum}
     */
    private Integer type;

    public AddSmsSignRequest toApplySmsSignRequest() {
        List<AddSmsSignRequest.AddSmsSignRequestSignFileList> signFileList = JacksonUtils.toObjs(fileList, AddSmsSignRequest.AddSmsSignRequestSignFileList.class);
        return new AddSmsSignRequest()
                .setSignName(getName())
                .setSignSource(source)
                .setRemark(remark)
                .setSignType(type)
                .setSignFileList(signFileList);
    }

    public ModifySmsSignRequest toModifySmsSignRequest() {
        List<ModifySmsSignRequest.ModifySmsSignRequestSignFileList> signFileList = JacksonUtils.toObjs(fileList, ModifySmsSignRequest.ModifySmsSignRequestSignFileList.class);
        return new ModifySmsSignRequest()
                .setSignName(getName())
                .setSignSource(source)
                .setRemark(remark)
                .setSignType(type)
                .setSignFileList(signFileList);
    }
}
