package org.vnjohn.sms.enums.tencent;

import lombok.Getter;

import java.util.Arrays;

/**
 * 腾讯云公共错误码
 * 参考：https://cloud.tencent.com/document/api/382/52075
 *
 * @author vnjohn
 * @since 2023/3/18
 */
@Getter
public enum TencentCommonCodeEnum {
    /**
     * 错误码以及对应描述、短信发送状态回执错误码
     */
    ACTION_OFFLINE("ActionOffline", "接口已下线"),
    INVALID_AUTHORIZATION("AuthFailure.InvalidAuthorization", "请求头部的 Authorization 不符合腾讯云标准"),
    INVALID_SECRET_ID("AuthFailure.InvalidSecretId", "密钥非法（不是云 API 密钥类型）"),
    DOMESTIC_NUMBER_NOT_SUPPORTED("isv.DOMESTIC_NUMBER_NOT_SUPPORTED", "国际/港澳台消息模板不支持发送境内号码"),
    MFA_FAILURE("AuthFailure.MFAFailure", "MFA 错误"),
    SECRET_ID_NOTFOUND("AuthFailure.SecretIdNotFound", "密钥不存在"),
    SIGNATURE_EXPIRE("AuthFailure.SignatureExpire", "签名过期"),
    SIGNATURE_FAILURE("AuthFailure.SignatureFailure", "签名错误"),
    TOKEN_FAILURE("AuthFailure.TokenFailure", "token 错误"),
    UNAUTHORIZED_OPERATION("AuthFailure.UnauthorizedOperation", "请求未授权"),
    DRY_RUN_OPERATION("DryRunOperation", "DryRun 操作，代表请求将会是成功的，只是多传了 DryRun 参数"),
    INVALID_PARAMETER("InvalidParameter", "参数错误（包括参数格式、类型等错误）"),
    IP_BLACK("IpInBlacklist", "IP地址在黑名单中"),
    IP_NOT_WHITE("IpNotInWhitelist", "IP地址不在白名单中"),
    MISSING_PARAMETER("MissingParameter", "缺少参数"),
    NO_SUCH_PRODUCT("NoSuchProduct", "产品不存在"),
    REQUEST_LIMIT_EXCEEDED("RequestLimitExceeded", "请求的次数超过了频率限制"),
    GLOBAL_REGION_LIMIT_EXCEEDED("RequestLimitExceeded.GlobalRegionUinLimitExceeded", "主账号超过频率限制"),
    CONTAIN_SENSITIVE_WORD("FailedOperation.ContainSensitiveWord", "短信内容中含有敏感词"),
    FAIL_RESOLVE_PACKET("FailedOperation.FailResolvePacket", "请求包解析失败"),
    FORBID_ADD_MARKETING_TEMPLATES("FailedOperation.ForbidAddMarketingTemplates", "个人用户不能申请营销短信"),
    BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "触发云通信流控限制"),
    INSUFFICIENT_BALANCE_IN_SMS_PACKAGE("FailedOperation.InsufficientBalanceInSmsPackage", "套餐包余量不足"),
    MARKETING_SEND_TIME_CONSTRAINT("FailedOperation.MarketingSendTimeConstraint", "营销短信发送时间限制，为避免骚扰用户，营销短信只允许在8点到22点发送"),
    PARAM_LENGTH_LIMIT("isv.PARAM_LENGTH_LIMIT", "参数超过长度限制"),
    MISSING_SIGNATURE("FailedOperation.MissingSignature", "没有申请签名之前，无法申请模板"),
    MISSING_SIGNATURE_LIST("FailedOperation.MissingSignatureList", "无法识别签名，请确认是否已有签名通过申请"),
    MISSING_SIGNATURE_TO_MODIFY("FailedOperation.MissingSignatureToModify", "此签名 ID 未提交申请或不存在，不能进行修改操作"),
    MISSING_TEMPLATE_LIST("FailedOperation.MissingTemplateList", "无法识别模板，请确认是否已有模板通过申请"),
    MISSING_TEMPLATE_TO_MODIFY("FailedOperation.MissingTemplateToModify", "此模板 ID 未提交申请或不存在，不能进行修改操作"),
    NOT_ENTERPRISE_CERTIFICATION("FailedOperation.NotEnterpriseCertification", "非企业认证无法使用签名及模板相关接口"),
    OTHER_ERROR("FailedOperation.OtherError", "其他错误，一般是由于参数携带不符合要求导致"),
    PARAMETERS_OTHER_ERROR("FailedOperation.ParametersOtherError", "未知错误，如有需要请联系"),
    PHONE_NUMBER_IN_BLACK_LIST("FailedOperation.PhoneNumberInBlacklist", "手机号在免打扰名单库中，通常是用户退订或者命中运营商免打扰名单导致的"),
    PHONE_NUMBER_PARSE_FAIL("FailedOperation.PhoneNumberParseFail", "号码解析失败，请检查号码是否符合 E.164 标准"),
    PROHIBIT_SUB_ACCOUNT_USE("FailedOperation.ProhibitSubAccountUse", "非主账号无法使用拉取模板列表功能。您可以使用主账号下云 API 密钥来调用接口"),
    SIGN_ID_NOT_EXIST("FailedOperation.SignIdNotExist", "签名 ID 不存在"),
    SIGN_NUMBER_LIMIT("FailedOperation.SignNumberLimit", "签名个数达到最大值"),
    SIGNATURE_INCORRECT_OR_UNAPPROVED("FailedOperation.SignatureIncorrectOrUnapproved", "签名未审批或格式错误"),
    TEMPLATE_ALREADY_PASSED_CHECK("FailedOperation.TemplateAlreadyPassedCheck", "此模板已经通过审核，无法再次进行修改"),
    TEMPLATE_ID_NOT_EXIST("FailedOperation.TemplateIdNotExist", "模板 ID 不存在"),
    TEMPLATE_INCORRECT_OR_UNAPPROVED("FailedOperation.TemplateIncorrectOrUnapproved", "模板未审批或内容不匹配"),
    TEMPLATE_NUMBER_LIMIT("FailedOperation.TemplateNumberLimit", "模板个数达到最大值"),
    TEMPLATE_PARAM_SET_NOT_MATCH_APPROVED_TEMPLATE("FailedOperation.TemplateParamSetNotMatchApprovedTemplate", "请求内容与审核通过的模板内容不匹配。请检查请求中模板参数的个数是否与申请的模板一致"),
    TEMPLATE_UNAPPROVED_OR_NOT_EXIST("FailedOperation.TemplateUnapprovedOrNotExist", "模板未审批或不存在"),
    JSON_PARSE_FAIL("InternalError.JsonParseFail", "解析用户参数失败"),
    INTERNAL_OTHER_ERROR("InternalError.OtherError", "其他错误，请联系 腾讯云短信小助手 并提供失败手机号"),
    SEND_AND_RECV_FAIL("InternalError.SendAndRecvFail", "接口超时或短信收发包超时，请检查您的网络是否有波动，或联系 腾讯云短信小助手 解决"),
    APPID_AND_BIZ_ID("InvalidParameter.AppidAndBizId", "账号与应用id不匹配"),
    DIRTY_WORD_FOUND("InvalidParameter.DirtyWordFound", "存在敏感词"),
    CONTENT_LENGTH_LIMIT("InvalidParameterValue.ContentLengthLimit", "请求的短信内容太长，短信长度规则请参考 国内短信内容长度计算规则"),
    IMAGE_INVALID("InvalidParameterValue.ImageInvalid", "上传的转码图片格式错误"),
    INCORRECT_PHONE_NUMBER("InvalidParameterValue.IncorrectPhoneNumber", "手机号格式错误"),
    INVALID_DOCUMENT_TYPE("InvalidParameterValue.InvalidDocumentType", "DocumentType 字段校验错误，请参照 API 接口说明中对改字段的说明，如有需要请联系"),
    INVALID_INTERNATIONAL("InvalidParameterValue.InvalidInternational", "International 字段校验错误，请参照 API 接口说明中对改字段的说明，如有需要请联系"),
    INVALID_SIGN_PURPOSE("InvalidParameterValue.InvalidSignPurpose", "SignPurpose 字段校验错误，请参照 API 接口说明中对改字段的说明，如有需要请联系"),
    INVALID_START_TIME("InvalidParameterValue.InvalidStartTime", "无效的拉取起始/截止时间，具体原因可能是请求的 SendDateTime 大于 EndDateTime"),
    INVALID_TEMPLATE_FORMAT("InvalidParameterValue.InvalidTemplateFormat", "模板格式错误"),
    INVALID_USED_METHOD("InvalidParameterValue.InvalidUsedMethod", "UsedMethod 字段校验错误，请参照 API 接口说明中对改字段的说明，如有需要请联系"),
    LIMIT_VERIFY_FAIL("InvalidParameterValue.LimitVerifyFail", "参数 Limit 校验失败"),
    OFFSET_VERIFY_FAIL("InvalidParameterValue.OffsetVerifyFail", "参数 Offset 校验失败"),
    PROHIBITED_USE_URL_IN_TEMPLATE_PARAMETER("InvalidParameterValue.ProhibitedUseUrlInTemplateParameter", "禁止在模板变量中使用 URL"),
    SDK_APP_ID_NOT_EXIST("InvalidParameterValue.SdkAppIdNotExist", "SdkAppId 不存在"),
    SIGN_ALREADY_PASSED_CHECK("InvalidParameterValue.SignAlreadyPassedCheck", "此签名已经通过审核，无法再次进行修改"),
    SIGN_EXIST_AND_UNAPPROVED("InvalidParameterValue.SignExistAndUnapproved", "已存在相同的待审核签名"),
    SIGN_NAME_LENGTH_TOO_LONG("InvalidParameterValue.SignNameLengthTooLong", "签名内容长度过长"),
    TEMPLATE_PARAMETER_LENGTH_LIMIT("InvalidParameterValue.TemplateParameterLengthLimit", "验证码模板参数格式错误，验证码类模板，模板变量只能传入0 - 6位（包括6位）纯数字"),
    TEMPLATE_WITH_DIRTY_WORDS("InvalidParameterValue.TemplateWithDirtyWords", "模板内容存在敏感词，请参考正文模板审核标准。"),
    APP_COUNTRY_OR_REGION_DAILY_LIMIT("LimitExceeded.AppCountryOrRegionDailyLimit", "业务短信国家/地区日下发条数超过设定的上限，可自行到控制台应用管理>基础配置下调整国际港澳台短信发送限制"),
    APP_COUNTRY_OR_REGION_IN_BLACKLIST("LimitExceeded.AppCountryOrRegionInBlacklist", "业务短信国家/地区不在国际港澳台短信发送限制设置的列表中而禁发，可自行到控制台应用管理>基础配置下调整国际港澳台短信发送限制"),
    APP_DAILY_LIMIT("LimitExceeded.AppDailyLimit", "业务短信日下发条数超过设定的上限 ，可自行到控制台调整短信频率限制策略"),
    APP_GLOBAL_DAILY_LIMIT("LimitExceeded.AppGlobalDailyLimit", "业务短信国际/港澳台日下发条数超过设定的上限，可自行到控制台应用管理>基础配置下调整发送总量阈值"),
    APP_MAINLAND_CHINA_DAILY_LIMIT("LimitExceeded.AppMainlandChinaDailyLimit", "业务短信中国大陆日下发条数超过设定的上限，可自行到控制台应用管理>基础配置下调整发送总量阈值"),
    DAILY_LIMIT("LimitExceeded.DailyLimit", "短信日下发条数超过设定的上限 (国际/港澳台)，如需调整限制，可联系 腾讯云短信小助手"),
    DELIVERY_FREQUENCY_LIMIT("LimitExceeded.DeliveryFrequencyLimit", "下发短信命中了频率限制策略，可自行到控制台调整短信频率限制策略，如有其他需求请联系 腾讯云短信小助手"),
    PHONE_NUMBER_COUNT_LIMIT("LimitExceeded.PhoneNumberCountLimit", "调用接口单次提交的手机号个数超过200个，请遵守 API 接口输入参数 PhoneNumberSet 描述"),
    PHONE_NUMBER_DAILY_LIMIT("LimitExceeded.PhoneNumberDailyLimit", "单个手机号日下发短信条数超过设定的上限，可自行到控制台调整短信频率限制策略"),
    PHONE_NUMBER_ONE_HOUR_LIMIT("LimitExceeded.DeliveryFrequencyLimit", "单个手机号1小时内下发短信条数超过设定的上限，可自行到控制台调整短信频率限制策略"),
    PHONE_NUMBER_SAME_CONTENT_DAILY_LIMIT("LimitExceeded.PhoneNumberSameContentDailyLimit", "单个手机号下发相同内容超过设定的上限，可自行到控制台调整短信频率限制策略"),
    PHONE_NUMBER_THIRTY_SECOND_LIMIT("LimitExceeded.PhoneNumberThirtySecondLimit", "单个手机号30秒内下发短信条数超过设定的上限，可自行到控制台调整短信频率限制策略"),
    EMPTY_PHONE_NUMBER_SET("MissingParameter.EmptyPhoneNumberSet", "传入的号码列表为空，请确认您的参数中是否传入号码"),
    INDIVIDUAL_USER_MARKETING_SMS_PERMISSION_DENY("UnauthorizedOperation.IndividualUserMarketingSmsPermissionDeny", "个人用户没有发营销短信的权限，请参考 权益区别"),
    SDK_APPID_IS_DISABLED("UnauthorizedOperation.SdkAppIdIsDisabled", "此 SdkAppId 禁止提供服务，如有需要请联系 腾讯云短信小助手"),
    SERVICE_SUSPEND_DUE_TO_ARREARS("UnauthorizedOperation.SerivceSuspendDueToArrears", "欠费被停止服务，可自行登录腾讯云充值来缴清欠款"),
    SMS_SDK_APPID_VERIFY_FAIL("UnauthorizedOperation.SmsSdkAppIdVerifyFail", "SmsSdkAppId 校验失败，请检查 SmsSdkAppId 是否属于 云API密钥 的关联账户"),
    CHINESE_MAINLAND_TEMPLATE_TO_GLOBAL_PHONE("UnsupportedOperation.ChineseMainlandTemplateToGlobalPhone", "国内短信模板不支持发送国际/港澳台手机号。发送国际/港澳台手机号请使用国际/港澳台短信正文模板"),
    CONTAIN_DOMESTIC_AND_INTERNATIONAL_PHONE_NUMBER("UnsupportedOperation.ContainDomesticAndInternationalPhoneNumber", "群发请求里既有国内手机号也有国际手机号。请排查是否存在（1）使用国内签名或模板却发送短信到国际手机号；（2）使用国际签名或模板却发送短信到国内手机号"),
    GLOBAL_TEMPLATE_TO_CHINESE_MAINLAND_PHONE("UnsupportedOperation.GlobalTemplateToChineseMainlandPhone", "国际/港澳台短信模板不支持发送国内手机号。发送国内手机号请使用国内短信正文模板"),
    UN_SUPPORTED_REGION("UnsupportedOperation.UnsuportedRegion", "不支持该地区短信下发"),
    ;

    private final String code;

    private final String message;

    TencentCommonCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static TencentCommonCodeEnum parseByCode(String code) {
        return Arrays.stream(TencentCommonCodeEnum.values()).filter(aliCommonCodeEnum -> aliCommonCodeEnum.code.equals(code)).findFirst().orElse(null);
    }

}
