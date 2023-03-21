package kmorelli.auriga.services.cache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import kmorelli.auriga.exceptions.AplicacaoException;

@Startup
@ApplicationScoped
public class CacheToken {

    private static Logger log = Logger.getLogger(CacheToken.class.getSimpleName());

    private final int expiracao;

    private AsyncCache<String, String> cache;
    private SecureRandom random;
    private MessageDigest digest;
    private Encoder base64Encoder;

    @Inject
    public CacheToken(CacheConfig cacheConfig) throws NoSuchAlgorithmException {
        random = new SecureRandom();
        digest = MessageDigest.getInstance("SHA-256");
        base64Encoder = Base64.getUrlEncoder();

        expiracao = cacheConfig.expiracao();
        log.info("Expiracao: " + expiracao);

        cache = Caffeine.newBuilder()
                .expireAfterWrite(expiracao, TimeUnit.SECONDS)
                .buildAsync();
    }

    public Uni<String> createToken() {

        byte binCode[] = new byte[8];
        random.nextBytes(binCode);
        String code = base64Encoder.encodeToString(binCode);
        String token = base64Encoder.encodeToString(digest.digest(binCode));

        CompletableFuture<String> futureToken = CompletableFuture.supplyAsync(() -> token);
        cache.put(code, futureToken);

        log.info("Criado o codigo: " + code);
        log.info("Criado o token: " + token);

        return Uni.createFrom().item(code);
    }

    public Uni<String> getToken(String code) {
        
        CompletableFuture<String> token = cache.getIfPresent(code);
        if (token == null) {
            return Uni.createFrom().failure(
                new AplicacaoException("001", "Nao foi possivel obter o token"));
        }
        cache.synchronous().invalidate(code);

        try {
            log.info("Encontrou o token: " + token.get());
        } catch (InterruptedException | ExecutionException e) {
            log.warning("O que aconteceu... " + e.getMessage());
        }

        return Uni.createFrom().completionStage(token).log();
    }

}
