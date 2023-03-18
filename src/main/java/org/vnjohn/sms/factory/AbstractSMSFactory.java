package org.vnjohn.sms.factory;

import org.vnjohn.sms.dto.*;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSShortLink;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
 * 短信服务抽象工厂类
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
    public abstract <T extends AbstractSMSSign> AbstractSMSSign createApplySign(ApplySignDTO applySignDTO);

    /**
     * 创建更新签名实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSSign> AbstractSMSSign createModifySign(ModifySignDTO modifySignDTO);

    /**
     * 创建删除签名实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSSign> AbstractSMSSign createRemoveSign(Long signId, String signName);

    /**
     * 创建查询签名状态实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSSign> AbstractSMSSign createQuerySignStatus(Long signId, String signName, Integer type);

    /**
     * 创建审核模版实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSTemplate> AbstractSMSTemplate createApplyTemplate(ApplyTemplateDTO applyTemplateDTO);

    /**
     * 创建更新模版实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSTemplate> AbstractSMSTemplate createModifyTemplate(ModifyTemplateDTO modifyTemplateDTO);

    /**
     * 创建删除模版实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSTemplate> AbstractSMSTemplate createRemoveTemplate(String code);

    /**
     * 创建查询模版状态实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSTemplate> AbstractSMSTemplate createQueryTemplateStatus(Integer type, String code);

    /**
     * 创建发送短信实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSSendSms> AbstractSMSSendSms createSendSms(SendSmsDTO sendSmsDTO);

    /**
     * 创建短链实体
     *
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractSMSShortLink> String createShortLink();

}
