package kmorelli.auriga.services.json;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;

@ApplicationScoped
public class JsonRedis {

    private final ValueCommands<String, String> cache;
    private int expiracao;

    @Inject
    Logger log;

    @Inject
    public JsonRedis(RedisDataSource redis, JsonConfig config) {
        cache = redis.value(String.class);
        expiracao = config.duracao();
    }

    public String get(String chave) {
        return cache.get(chave);
    }

    public void set(String chave, String valor) {
        cache.setex(chave, expiracao, valor);
        log.info("Expiracao do json no redis: " + expiracao);
    }
    
}
