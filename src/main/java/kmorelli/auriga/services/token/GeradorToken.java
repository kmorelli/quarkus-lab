package kmorelli.auriga.services.token;

import java.security.PrivateKey;
import java.util.Map;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.util.KeyUtils;

public class GeradorToken {

    final private JwtClaimsBuilder builder;
    //final private PrivateKey privateKey;

    public GeradorToken(Map<String, Object> claims) {
        if (claims != null) {
            builder = Jwt.claims(claims);
        } else {
            builder = Jwt.claims();
        }

    }

    //public String geraToken() {
    //    return builder.jws().sign(null)
    //}

}
