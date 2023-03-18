package org.vnjohn.sms.service;

import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.response.ApplyStatusResponse;

/**
 * 抽象短信服务公共能力
 *
 * @author vnjohn
 * @since 2023/3/17
 */
public abstract class AbstractSMSService {
    /**
     * 申请签名
     *
     * @param applySmsSign
     * @param <T>
     */
    public <T extends AbstractSMSSign> String applySign(AbstractSMSSign applySmsSign) {
        return null;
    }

    /**
     * 更新签名
     *
     * @param modifySmsSign
     * @param <T>
     */
    public <T extends AbstractSMSSign> String modifySign(AbstractSMSSign modifySmsSign) {
        return null;
    }

    /**
     * 删除签名
     *
     * @param removeSmsSign
     * @param <T>
     */
    public <T extends AbstractSMSSign> String removeSign(AbstractSMSSign removeSmsSign) {
        return null;
    }

    /**
     * 查询签名审核状态
     *
     * @param statusSmsSign
     * @param <T>
     * @return 返回审核是否成功信息，返回审核失败的原因
     */
    public <T extends AbstractSMSSign> ApplyStatusResponse querySignApplyStatus(AbstractSMSSign statusSmsSign) {
        return null;
    }

    /**
     * 申请模版
     * @param applySmsTemplate
     * @param <T>
     * @return 返回模版唯一标识
     */
    public <T extends AbstractSMSTemplate> String applyTemplate(AbstractSMSTemplate applySmsTemplate) {
        return null;
    }

    /**
     * 更新模版
     *
     * @param modifySmsTemplate
     * @param <T>
     */
    public <T extends AbstractSMSTemplate> String modifyTemplate(AbstractSMSTemplate modifySmsTemplate) {
        return null;
    }

    /**
     * 删除模版
     *
     * @param removeSmsTemplate
     * @param <T>
     */
    public <T extends AbstractSMSTemplate> String removeTemplate(AbstractSMSTemplate removeSmsTemplate) {
        return null;
    }

    /**
     * 查询模版审核状态
     *
     * @param statusSmsTemplate
     * @param <T>
     * @return 返回审核是否成功信息，返回审核失败的原因
     */
    public <T extends AbstractSMSTemplate> ApplyStatusResponse queryTemplateApplyStatus(AbstractSMSTemplate statusSmsTemplate) {
        return null;
    }

}
