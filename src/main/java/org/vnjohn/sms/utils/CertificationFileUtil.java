package org.vnjohn.sms.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.multipart.MultipartFile;
import org.vnjohn.sms.SmsBusinessException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static org.vnjohn.sms.SmsBusinessException.throwBusinessException;

/**
 * 认证文件操作工具类
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Slf4j
public class CertificationFileUtil {
    // 签名的证明文件格式，支持上传多张图片。当前支持JPG、PNG、GIF或JPEG格式的图片
    private final static List<String> IMG_SUFFIX_LIST = Arrays.asList("jpg", "png", "gif", "jpeg");

    public static String getFileSuffix(MultipartFile file) {
        // 获取文件后缀名的代码
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        checkSuffix(extension);
        return extension;
    }

    public static void checkSuffix(String suffix) {
        if (!IMG_SUFFIX_LIST.contains(suffix)) {
            throwBusinessException("认证文件类型不支持，仅支持：" + Strings.join(IMG_SUFFIX_LIST, ','));
        }
    }

    public static String encryptFileToBase64(MultipartFile file) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data;
        // 输入流
        InputStream is;
        try {
            is = file.getInputStream();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int t;
            byte[] bytes = new byte[1024];
            while ((t = is.read(bytes)) != -1) {
                swapStream.write(bytes, 0, t);
            }
            data = swapStream.toByteArray();
            return Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("CertificationFileUtil#encryptToBase64 file->base64 error {}", e.getMessage());
            throw new SmsBusinessException("认证文件加密失败");
        }
    }

}
