package org.vnjohn.sms;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsProviderVnjohnApplicationTests {

    @Test
    public void contextLoads() {
        File file = new File("/Users/vnjohn/Pictures/spring-boot.png");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
