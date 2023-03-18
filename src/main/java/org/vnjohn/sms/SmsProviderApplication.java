package org.vnjohn.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 关于 @SuperBuilder 注解使用注意以下几点：
 * 1、子类使用了它，其父类也必须加上
 * 2、子类若无参数，不需要写 @NoArgsConstructor 注解
 * 3、由父类统一写 @NoArgsConstructor、@AllArgsConstructor
 */
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class SmsProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsProviderApplication.class, args);
    }

}
