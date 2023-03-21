package kmorelli.auriga.services.extension;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AutenticarExtension {
    
    private final String uuid;

    public AutenticarExtension() {
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

}
