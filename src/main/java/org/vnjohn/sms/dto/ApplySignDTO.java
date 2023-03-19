package org.vnjohn.sms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.vnjohn.sms.enums.SMSSignPurposeEnum;
import org.vnjohn.sms.enums.SMSSignSourceEnum;
import org.vnjohn.sms.enums.SMSSignTypeEnum;
import org.vnjohn.sms.enums.tencent.TencentDocumentTypeEnum;

import java.io.Serializable;

/**
 * 审核签名的唯一入口，要适配其他云
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Data
public class ApplySignDTO implements Serializable {
    private static final long serialVersionUID = 3499350840798574235L;

    /**
     * 签名名称
     */
    private String name;

    /**
     * 备注/说明
     */
    private String remark;

    /**
     * 签名来源
     * {@link SMSSignSourceEnum}
     */
    private Integer source;

    /**
     * 认证文件，图片
     */
    private MultipartFile certificationImaFile;

    /**
     * 委托授权证明，图片
     * 委托授权证明图，签名用途为 1 时需要
     */
    private MultipartFile commissionImgFile;

    /**
     * 签名类型
     * {@link SMSSignTypeEnum}
     */
    private Integer type;

    /**
     * 认证类型
     * {@link TencentDocumentTypeEnum}
     */
    private Integer certificationType;

    /**
     * 签名用途
     * {@link SMSSignPurposeEnum}
     */
    private Integer purpose;

}
