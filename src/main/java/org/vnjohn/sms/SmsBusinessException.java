package org.vnjohn.sms;

/**
 * @author vnjohn
 * @since 2023/3/17
 */
public class SmsBusinessException extends RuntimeException {

    public SmsBusinessException(String message) {
        super(message);
    }

    public static void throwNull(Object obj, String message) {
        if (null == obj) {
            throw new SmsBusinessException(message);
        }
    }

    public static void throwBusinessException(String message) {
        throw new SmsBusinessException(message);
    }

}
