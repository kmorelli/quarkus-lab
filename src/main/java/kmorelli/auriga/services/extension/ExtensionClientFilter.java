package kmorelli.auriga.services.extension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.NewCookie;

import org.jboss.logging.Logger;

public class ExtensionClientFilter implements ClientRequestFilter, ClientResponseFilter {

    private Map<String, String> cookies = new ConcurrentHashMap<String, String>();

    private Logger log = Logger.getLogger(ExtensionClientFilter.class.getName());

    public ExtensionClientFilter() {
        cookies.put("EXPERIMENT", "EXPERIMENT=lllalalalakkk;Version=1;Path=/;Secure;HttpOnly");
    }

    /** Este método atende o fluxo de requisicao */
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        final String cookie = cookies.entrySet().stream()
                .map(v -> v.getValue().toString())
                .collect(Collectors.joining("; "));

        log.info("Cookie serializado: " + cookie);
        requestContext.getHeaders().putSingle("Cookie", cookie);
    }

    /** Este método atende o fluxo de resposta */
    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {

        Map<String, NewCookie> cookiesResposta = responseContext.getCookies();

        if (cookiesResposta == null) {
            return;
        }

        // Pegar os cookies da resposta e atualizar a lista de cookies
        cookiesResposta.forEach((k, v) -> {
            final String cookie = v.toString();
            if (!cookie.equals(cookies.get(k))) {
                log.info("Cookie " + k + ": " + cookie);
                cookies.put(k, cookie);
            }
        });

        // Inserir url de origem quando der erro
        if (responseContext.getStatus() < 400) {
            return;
        }

        JsonObjectBuilder erroBuilder = Json.createObjectBuilder();

        if (responseContext.hasEntity()) {

            String textoErro = new String(responseContext.getEntityStream().readAllBytes());
            log.info("Erro: " + textoErro);

            try {
                JsonReader jsonReader = Json.createReader(responseContext.getEntityStream());
                log.info("Erro json: " + jsonReader.readObject());
                erroBuilder.addAll(Json.createObjectBuilder(jsonReader.readObject()));
            } catch (JsonException e) {
                log.warn("Nao foi possivel ler o erro: " + e.getLocalizedMessage()); 
            }
        }

        JsonObject erro = erroBuilder.add("origem", requestContext.getUri().getPath()).build();

        responseContext.setEntityStream(new ByteArrayInputStream(erro.toString().getBytes()));

    }

}
