package org.vnjohn.sms.response.huawei;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vnjohn
 * @since 2023/6/3
 */
@Data
public class QuerySignResponse extends BaseResponse {
    private List<Result> results;


    @Data
    public static class Result {
        /**
         * 应用主键ID，用于获取、修改应用的唯一标识
         */
        private String id;

        @JsonProperty("signature_id")
        private String signatureId;

        /**
         * 应用名称
         */
        @JsonProperty("signature_name")
        private String signatureName;

        /**
         * 应用key
         */
        @JsonProperty("app_key")
        private String appKey;

        /**
         * 创建时间
         */
        @JsonProperty("create_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private LocalDateTime createTime;

        /**
         * 更新时间
         */
        @JsonProperty("update_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private LocalDateTime updateTime;

        /**
         * 租户customer id
         */
        @JsonProperty("customer_id")
        private String customerId;

        /**
         * 租户resource id
         */
        @JsonProperty("resource_id")
        private String resourceId;

    }

}
