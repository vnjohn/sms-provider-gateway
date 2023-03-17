package org.vnjohn.sms.constants;

import lombok.Getter;

/**
 * 阿里云公共错误码
 * 参考：https://help.aliyun.com/document_detail/101346.html
 *
 * @author vnjohn
 * @since 2023/3/17
 */
@Getter
public enum AliCloudCommonErrorCodeEnum {
    /**
     * 错误码以及对应描述、短信发送状态回执错误码
     */
    OK("OK", "成功"),
    DELIVERED("DELIVERED", "发送成功"),
    SMS_SIGNATURE_SCENE_ILLEGAL("isv.SMS_SIGNATURE_SCENE_ILLEGAL", "签名和模板类型不一致"),
    EXTEND_CODE_ERROR("isv.EXTEND_CODE_ERROR", "扩展码使用错误，相同的扩展码不可用于多个签名"),
    DOMESTIC_NUMBER_NOT_SUPPORTED("isv.DOMESTIC_NUMBER_NOT_SUPPORTED", "国际/港澳台消息模板不支持发送境内号码"),
    DENY_IP_RANGE("isv.DENY_IP_RANGE", "源IP地址所在的地区被禁用"),
    DAY_LIMIT_CONTROL("isv.DAY_LIMIT_CONTROL", "触发日发送限额"),
    SMS_CONTENT_ILLEGAL("isv.SMS_CONTENT_ILLEGAL", "短信内容包含禁止发送内容"),
    SMS_SIGN_ILLEGAL("isv.SMS_SIGN_ILLEGAL", "签名禁止使用"),
    RAM_PERMISSION_DENY("isp.RAM_PERMISSION_DENY", "RAM权限不足"),
    OUT_OF_SERVICE("isv.OUT_OF_SERVICE", "业务停机"),
    PRODUCT_UN_SUBSCRIPT("isv.PRODUCT_UN_SUBSCRIPT", "未开通云通信产品的阿里云客户"),
    PRODUCT_UNSUBSCRIBE("isv.PRODUCT_UNSUBSCRIBE", "产品未开通"),
    ACCOUNT_NOT_EXISTS("isv.ACCOUNT_NOT_EXISTS", "账户不存在"),
    ACCOUNT_ABNORMAL("isv.ACCOUNT_ABNORMAL", "账户异常"),
    SMS_TEMPLATE_ILLEGAL("isv.SMS_TEMPLATE_ILLEGAL", "该账号下找不到对应模板"),
    SMS_SIGNATURE_ILLEGAL("isv.SMS_SIGNATURE_ILLEGAL", "该账号下找不到对应签名"),
    INVALID_PARAMETERS("isv.INVALID_PARAMETERS", "参数格式不正确"),
    SYSTEM_ERROR("isp.SYSTEM_ERROR", "系统出现错误，请重新调用"),
    MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL", "手机号码格式错误"),
    MOBILE_COUNT_OVER_LIMIT("isv.MOBILE_COUNT_OVER_LIMIT", "手机号码数量超过限制，最多支持1000条"),
    TEMPLATE_MISSING_PARAMETERS("isv.TEMPLATE_MISSING_PARAMETERS", "模板变量中存在未赋值变量"),
    BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "触发云通信流控限制"),
    INVALID_JSON_PARAM("isv.INVALID_JSON_PARAM", "参数格式错误，请修改为字符串值"),
    BLACK_KEY_CONTROL_LIMIT("isv.BLACK_KEY_CONTROL_LIMIT", "变量中传入疑似违规信息"),
    PARAM_LENGTH_LIMIT("isv.PARAM_LENGTH_LIMIT", "参数超过长度限制"),
    PARAM_NOT_SUPPORT_URL("isv.PARAM_NOT_SUPPORT_URL", "变量不支持传入URL"),
    AMOUNT_NOT_ENOUGH("isv.AMOUNT_NOT_ENOUGH", "账户余额不足"),
    TEMPLATE_PARAMS_ILLEGAL("isv.TEMPLATE_PARAMS_ILLEGAL", "传入的变量内容和实际申请模板时变量所选择的属性类型不配"),
    SIGNATURE_NOT_MATCH("SignatureDoesNotMatch", "客户端生成的签名与服务端不匹配"),
    INVALID_TIMESTAMP("InvalidTimeStamp.Expired", "时间戳或日期已过期"),
    SIGNATURE_NONCE_USED("SignatureNonceUsed", "签名随机数已被使用"),
    INVALID_VERSION("InvalidVersion", "API版本号错误"),
    ACTION_NOT_FOUND("InvalidAction.NotFound", "未找到指定的API，请检查您的URL和方法"),
    SIGN_COUNT_OVER_LIMIT("isv.SIGN_COUNT_OVER_LIMIT", "超过单自然日签名申请数量上限"),
    TEMPLATE_COUNT_OVER_LIMIT("isv.TEMPLATE_COUNT_OVER_LIMIT", "超过单自然日模板申请数量上限"),
    SIGN_NAME_ILLEGAL("isv.SIGN_NAME_ILLEGAL", "签名名称不符合规范"),
    SIGN_FILE_LIMIT("isv.SIGN_FILE_LIMIT", "签名认证材料附件大小超过限制"),
    SIGN_OVER_LIMIT("isv.SIGN_OVER_LIMIT", "签名字符数量超过限制"),
    TEMPLATE_OVER_LIMIT("isv.TEMPLATE_OVER_LIMIT", "模板字符数量超过限制"),
    SIGNATURE_BLACKLIST("SIGNATURE_BLACKLIST", "签名内容涉及违规信息"),
    SHORTURL_OVER_LIMIT("isv.SHORTURL_OVER_LIMIT", "超过单自然日短链申请数量上限"),
    NO_AVAILABLE_SHORT_URL("isv.NO_AVAILABLE_SHORT_URL", "该账号无有效短链"),
    SHORTURL_NAME_ILLEGAL("isv.SHORTURL_NAME_ILLEGAL", "短链名不能超过13字符"),
    SOURCEURL_OVER_LIMIT("isv.SOURCEURL_OVER_LIMIT", "原始链接字符数量超过限制"),
    SHORTURL_TIME_ILLEGAL("isv.SHORTURL_TIME_ILLEGAL", "短链有效期期限超过限制"),
    PHONENUMBERS_OVER_LIMIT("isv.PHONENUMBERS_OVER_LIMIT", "上传手机号个数超过上限"),
    SHORTURL_STILL_AVAILABLE("isv.SHORTURL_STILL_AVAILABLE", "原始链接生成的短链仍在有效期内"),
    ERROR_EMPTY_FILE("isv.ERROR_EMPTY_FILE", "签名文件为空"),
    GATEWAY_ERROR("isp.GATEWAY_ERROR", "调用发送应用模块失败"),
    ERROR_SIGN_NOT_DELETE("isv.ERROR_SIGN_NOT_DELETE", "审核中的签名，暂时无法删除"),
    ERROR_SIGN_NOT_MODIFY("isv.ERROR_SIGN_NOT_MODIFY", "已通过的签名不支持修改"),
    ERROR_TEMPLATE_NOT_DELETE("isv.ERROR_TEMPLATE_NOT_DELETE", "审核中的模板，暂时无法删除"),
    ERROR_TEMPLATE_NOT_MODIFY("isv.ERROR_TEMPLATE_NOT_MODIFY", "已通过的模板不支持修改"),
    SMS_OVER_LIMIT("isv.SMS_OVER_LIMIT", "单日最多申请模板或签名100条"),
    CUSTOMER_REFUSED("isv.CUSTOMER_REFUSED", "用户已退订推广短信"),
    SMS_TEST_SIGN_TEMPLATE_LIMIT("isv.SMS_TEST_SIGN_TEMPLATE_LIMIT", "测试模板和签名限制"),
    SHORTURL_DOMAIN_EMPTY("isv.SHORTURL_DOMAIN_EMPTY", "短链创建失败"),
    TEMPLATE_PARAMETER_COUNT_ILLEGAL("template_parameter_count_illegal", "验证码模板仅支持一个验证码作为变量"),
    SMS_TEST_TEMPLATE_PARAMS_ILLEGAL("isv.SMS_TEST_TEMPLATE_PARAMS_ILLEGAL", "测试专用模板中的变量仅支持4~6位纯数字"),
    SMS_TEST_NUMBER_LIMIT("isv.SMS_TEST_NUMBER_LIMIT", "只能向已回复授权信息的手机号发送"),
    SMS_SIGN_EMOJI_ILLEGAL("isv.SMS_SIGN_EMOJI_ILLEGAL", "签名不能包含emoji表情"),
    SECURITY_FROZEN_ACCOUNT("isv.SECURITY_FROZEN_ACCOUNT", "因该账号长时间未使用，出于对您的账号安全考虑，已限制您账号的短信发送"),
    IS_CLOSE("IS_CLOSE", "短信下发时通道被关停"),
    PARAMS_ILLEGAL("PARAMS_ILLEGAL", "参数错误");

    private final String code;

    private final String message;

    AliCloudCommonErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AliCloudCommonErrorCodeEnum parseByCode(String code) {
        for (AliCloudCommonErrorCodeEnum errorCodeEnum : AliCloudCommonErrorCodeEnum.values()) {
            if (errorCodeEnum.code.equals(code)) {
                return errorCodeEnum;
            }
        }
        return null;
    }

}
