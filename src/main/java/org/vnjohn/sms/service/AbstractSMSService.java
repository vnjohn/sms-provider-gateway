package org.vnjohn.sms.service;

import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.response.SignStatusResponse;

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
     */
    public <T extends AbstractSMSSign> SignStatusResponse querySignApplyStatus(AbstractSMSSign statusSmsSign) {
        return null;
    }

}
