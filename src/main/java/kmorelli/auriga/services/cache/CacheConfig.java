package kmorelli.auriga.services.cache;

import io.smallrye.config.ConfigMapping;

/** Configurações cache.* */
@ConfigMapping(prefix = "cache")
public interface CacheConfig {
    //cache.expiracao
    int expiracao();
}
