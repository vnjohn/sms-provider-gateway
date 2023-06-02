package org.vnjohn.sms.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.vnjohn.sms.constants.Constants;

import java.time.Duration;

import static java.lang.Long.parseLong;

/**
 * @author vnjohn
 * @since 2023/6/3
 */
@Slf4j
public class CustomRedisCacheManager extends RedisCacheManager {

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @NotNull
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String poundKey = Constants.POUND_KEY;
        if (!name.contains(poundKey)) {
            return super.createRedisCache(name, cacheConfig);
        }
        String[] cacheNameArray = name.split(poundKey);
        cacheConfig = cacheTTL(cacheNameArray[1], cacheConfig);
        return super.createRedisCache(cacheNameArray[0], cacheConfig);
    }

    private RedisCacheConfiguration cacheTTL(String ttlStr, @NotNull RedisCacheConfiguration cacheConfig) {
        // 根据传参设置缓存失效时间
        Duration duration = parseDuration(ttlStr);
        return cacheConfig.entryTtl(duration);
    }

    private Duration parseDuration(String ttl) {
        String timeUnit = ttl.substring(ttl.length() - 1);
        int timeUnitIndex = ttl.indexOf(timeUnit);
        String ttlTime = ttl.substring(0, timeUnitIndex);
        switch (timeUnit) {
            case "d":
                return Duration.ofDays(parseLong(ttlTime));
            case "h":
                return Duration.ofHours(parseLong(ttlTime));
            case "m":
                return Duration.ofMinutes(parseLong(ttlTime));
            default:
                return Duration.ofSeconds(parseLong(ttlTime));
        }
    }
}
