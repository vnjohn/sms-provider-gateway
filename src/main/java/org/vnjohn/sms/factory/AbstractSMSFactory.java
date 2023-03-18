package org.vnjohn.sms.factory;

import org.vnjohn.sms.dto.ApplySignDTO;
import org.vnjohn.sms.dto.ApplyTemplateDTO;
import org.vnjohn.sms.dto.ModifySignDTO;
import org.vnjohn.sms.dto.ModifyTemplateDTO;
import org.vnjohn.sms.entity.AbstractSMSShortLink;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
 * 短信服务抽象工厂类，默认空实现
 *
 * @author vnjohn
 * @since 2023/3/17
 */
public abstract class AbstractSMSFactory {
    /**
     * 创建待审核签名实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSSign> AbstractSMSSign createApplySign(ApplySignDTO applySignDTO) {
        return null;
    }

    /**
     * 创建更新签名实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSSign> AbstractSMSSign createModifySign(ModifySignDTO modifySignDTO) {
        return null;
    }

    /**
     * 创建删除签名实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSSign> AbstractSMSSign createRemoveSign(Long signId, String signName) {
        return null;
    }

    /**
     * 创建查询签名状态实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSSign> AbstractSMSSign createQuerySignStatus(Long signId, String signName) {
        return null;
    }

    /**
     * 创建审核模版实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSTemplate> AbstractSMSTemplate createApplyTemplate(ApplyTemplateDTO applyTemplateDTO) {
        return null;
    }

    /**
     * 创建更新模版实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSTemplate> AbstractSMSTemplate createModifyTemplate(ModifyTemplateDTO modifyTemplateDTO) {
        return null;
    }

    /**
     * 创建删除模版实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSTemplate> AbstractSMSTemplate createRemoveTemplate(String code) {
        return null;
    }

    /**
     * 创建查询模版状态实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSTemplate> AbstractSMSTemplate createQueryTemplateStatus(Integer type, String code) {
        return null;
    }

    /**
     * 创建短链实体
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractSMSShortLink> AbstractSMSSign createShortLink() {
        return null;
    }

}
