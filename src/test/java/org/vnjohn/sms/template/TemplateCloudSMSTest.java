package org.vnjohn.sms.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vnjohn.sms.dto.ApplyTemplateDTO;
import org.vnjohn.sms.entity.AbstractSMSTemplate;
import org.vnjohn.sms.factory.AbstractSMSFactory;
import org.vnjohn.sms.service.AbstractSMSService;

import javax.annotation.Resource;

/**
 * @author vnjohn
 * @since 2023/3/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateCloudSMSTest {
    @Resource
    private AbstractSMSFactory smsFactory;

    @Resource
    private AbstractSMSService smsService;

    @Test
    public void createTemplate() {
        ApplyTemplateDTO applyTemplateDTO = new ApplyTemplateDTO();

        AbstractSMSTemplate applyTemplate = smsFactory.createApplyTemplate(applyTemplateDTO);
        smsService.applyTemplate(applyTemplate);
    }
}
