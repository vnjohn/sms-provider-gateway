package org.vnjohn.sms.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Cacheable 是使用AOP 代理实现的 ，通过创建内部类来代理缓存方法，
 * 这样就会导致一个问题，类内部的方法调用类内部的缓存方法不会走代理，不会走代理，就不能正常创建缓存
 *
 * @author vnjohn
 * @since 2023/6/3
 */
@Service
public class CacheService {

    @Cacheable(value = "vn#10s")
    public String cache() {
        return "vnjohn";
    }
}
