package org.vnjohn.sms.utils;

import org.springframework.expression.ParseException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 短信模版解析器
 * 基于 Spring SpelExpressionParser 表达式解析器源码改造
 *
 * @author vnjohn
 * @since 2023/3/17
 */
public class SmsTemplateContentParser {
    protected static final String FIELD_ALI_PREFIX = "${";
    protected static final String FIELD_PREFIX = "{";
    protected static final String FIELD_ALI_SUFFIX = "}";

    /**
     * 纯验证模版内容是否符合规则
     *
     * @param smsContent 模版内容
     */
    public static void parseAliSmsContext(String smsContent) {
        parseAliSmsContextParams(smsContent, Collections.emptyList());
    }

    /**
     * 用于阿里云模版传递参数值
     *
     * @param templateContent
     * @param templateParamSet
     * @return
     */
    public static Map<String, String> parseAliSmsContextParams(String templateContent, List<String> templateParamSet) {
        int startIdx = 0;
        int paramFoundIndex = 0;
        Map<String, String> paramsNames = new HashMap<>(templateParamSet.size());
        while (startIdx < templateContent.length()) {
            int noMatchIndex = templateContent.indexOf(FIELD_PREFIX, startIdx);
            int prefixIndex = templateContent.indexOf(FIELD_ALI_PREFIX, startIdx);
            // 未匹配 { 直接比较，否则用 { 下标 -1 做对比
            boolean noCollision = (noMatchIndex == -1 ? noMatchIndex : (noMatchIndex - 1)) != prefixIndex;
            if (prefixIndex == -1 && noMatchIndex > -1 || noCollision) {
                throw new ParseException(templateContent, startIdx, "No ending prefix'" + FIELD_ALI_PREFIX + "' found in templateContext field");
            }
            if (prefixIndex == -1) {
                break;
            }
            if (prefixIndex >= startIdx) {
                int afterPrefixIndex = prefixIndex + FIELD_ALI_PREFIX.length();
                int suffixIndex = skipToCorrectEndSuffix(templateContent, afterPrefixIndex);
                if (suffixIndex == -1) {
                    throw new ParseException(templateContent, prefixIndex, "No ending suffix '" + FIELD_ALI_SUFFIX + "' templateContext");
                }
                if (suffixIndex == afterPrefixIndex) {
                    throw new ParseException(templateContent, prefixIndex,
                            "No expression defined within delimiter '" + FIELD_ALI_PREFIX + FIELD_ALI_SUFFIX +
                                    "' at character " + prefixIndex);
                }
                String expr = templateContent.substring(prefixIndex + FIELD_ALI_PREFIX.length(), suffixIndex);
                expr = expr.trim();
                if (expr.isEmpty()) {
                    throw new ParseException(templateContent, prefixIndex, "No expression defined within delimiter '" + FIELD_ALI_PREFIX + FIELD_ALI_SUFFIX + "' at character " + prefixIndex);
                }
                if (!templateParamSet.isEmpty()) {
                    paramsNames.put(expr, templateParamSet.get(paramFoundIndex));
                }
                paramFoundIndex++;
                startIdx = suffixIndex + FIELD_ALI_SUFFIX.length();
            }
        }
        return paramsNames;
    }

    private static boolean isSuffixHere(String expressionString, int pos) {
        int suffixPosition = 0;
        for (int i = 0; i < FIELD_ALI_SUFFIX.length() && pos < expressionString.length(); i++) {
            if (expressionString.charAt(pos++) != FIELD_ALI_SUFFIX.charAt(suffixPosition++)) {
                return false;
            }
        }
        // the expressionString ran out before the suffix could entirely be found
        return suffixPosition == FIELD_ALI_SUFFIX.length();
    }

    /**
     * 生成 6 位随机验证码
     *
     * @return
     */
    public static String generateCode() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

    private static int skipToCorrectEndSuffix(String expressionString, int afterPrefixIndex) throws ParseException {
        int pos = afterPrefixIndex;
        int maxLen = expressionString.length();
        int nextSuffix = expressionString.indexOf(FIELD_ALI_SUFFIX, afterPrefixIndex);
        if (nextSuffix == -1) {
            return -1;
        }
        while (pos < maxLen) {
            if (isSuffixHere(expressionString, pos)) {
                break;
            }
            pos++;
        }
        return pos;
    }

}
