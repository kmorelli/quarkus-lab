package kmorelli.auriga.services.token;

import java.security.KeyPair;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class ChavesToken {

    @ConfigProperty(name = "jwk.token")
    String jwkString;
    
    private String latestKid;
    private Map<String, KeyPair> chaves;

}
