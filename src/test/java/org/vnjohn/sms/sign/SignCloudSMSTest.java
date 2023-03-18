package org.vnjohn.sms.sign;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.vnjohn.sms.dto.ApplySignDTO;
import org.vnjohn.sms.entity.AbstractSMSSign;
import org.vnjohn.sms.enums.SMSSignPurposeEnum;
import org.vnjohn.sms.enums.SMSSignSourceEnum;
import org.vnjohn.sms.enums.SMSSignTypeEnum;
import org.vnjohn.sms.enums.tencent.TencentDocumentTypeEnum;
import org.vnjohn.sms.factory.AbstractSMSFactory;
import org.vnjohn.sms.service.AbstractSMSService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 通过测试类来模拟控制器
 *
 * @author vnjohn
 * @since 2023/3/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SignCloudSMSTest {
    @Resource
    private AbstractSMSFactory smsFactory;

    @Resource
    private AbstractSMSService smsService;

    public static MultipartFile getMultipartFile(String path) {
        File file = new File(path);
        // 文件会在哪个目录创建
        FileItem fileItem;
        try {
            fileItem = new DiskFileItem(
                    "file",//form表单文件控件的名字随便起
                    Files.probeContentType(file.toPath()),//文件类型
                    false, //是否是表单字段
                    file.getName(),//原始文件名
                    (int) file.length(),
                    file.getParentFile());
            //最关键的一步：为 DiskFileItem OutputStream 赋值
            // IOUtils org.apache.commons.io.IOUtils;
            //与此类似的还有 FileUtils
            IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
            MultipartFile cMultiFile = new CommonsMultipartFile(fileItem);
            System.out.println(cMultiFile.getOriginalFilename());
            return cMultiFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void createSign() {
        ApplySignDTO applySignDTO = new ApplySignDTO();
        applySignDTO.setFile(getMultipartFile("/Users/vnjohn/Pictures/vnjohn-website.png"));
        applySignDTO.setName("vnjohn");
        applySignDTO.setPurpose(SMSSignPurposeEnum.PERSONAL_USE.getCode());
        applySignDTO.setSource(SMSSignSourceEnum.WEBSITE.getCode());
        applySignDTO.setRemark("https://www.vnjohn.com");
        // 若为阿里云无须传递
        applySignDTO.setCertificationType(TencentDocumentTypeEnum.BACK_WEBSITE_RECORDS.getCode());
        applySignDTO.setType(SMSSignTypeEnum.OUTSIDE.getCode());
        AbstractSMSSign applySign = smsFactory.createApplySign(applySignDTO);
        smsService.applySign(applySign);
    }
}
