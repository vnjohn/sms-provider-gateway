package org.vnjohn.sms;


import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;

/**
 * 短信业务异常类
 *
 * @author vnjohn
 * @since 2023/3/17
 */
public class SmsBusinessException extends RuntimeException {

    public SmsBusinessException(String message) {
        super(message);
    }

    public static void throwNull(Object obj, String message) {
        if (null == obj) {
            throwBusinessException(message);
        }
    }

    public static void throwEmpty(Object[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throwBusinessException(message);
        }
    }

    public static void throwEmpty(Collection<?> collection, String message) {
        if (null == collection || collection.isEmpty()) {
            throwBusinessException(message);
        }
    }

    public static void throwBusinessException(String message) {
        throw new SmsBusinessException(message);
    }

}
