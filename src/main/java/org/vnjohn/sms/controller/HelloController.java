package org.vnjohn.sms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vnjohn.sms.service.CacheService;

import javax.annotation.Resource;

/**
 * @author vnjohn
 * @since 2023/6/3
 */
@RestController
public class HelloController {

    @Resource
    private CacheService cacheService;

    @GetMapping("get/cache")
    public String getCache() {
        return cacheService.cache();
    }
}
