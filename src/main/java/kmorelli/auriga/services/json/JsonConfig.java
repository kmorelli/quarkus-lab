package kmorelli.auriga.services.json;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "json")
public interface JsonConfig {
    //json.duracao
    int duracao();
}