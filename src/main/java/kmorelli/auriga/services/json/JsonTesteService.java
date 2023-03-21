package kmorelli.auriga.services.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyOrderStrategy;

import org.jboss.logging.Logger;

import kmorelli.auriga.exceptions.AplicacaoException;

@ApplicationScoped
public class JsonTesteService {

    private static final Pattern REGEX_FORMATO_EMAIL = Pattern.compile("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern REGEX_MASCARA_EMAIL = Pattern.compile("(?<=.)[^@](?=[^@]*?@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*\\.)");

    private Jsonb jsonMapper;

    @Inject
    Logger log;

    @Inject
    JsonRedis redis;

    public JsonTesteService() {
        JsonbConfig jsonbConfig = new JsonbConfig()
                .withPropertyOrderStrategy(PropertyOrderStrategy.ANY);
        jsonMapper = JsonbBuilder.create(jsonbConfig);
    }

    public RespostaJson testarJson() {

        RespostaJson resposta = new RespostaJson(10, "correntista");
        resposta.addMeioComunicacao(new MeioComunicacao(1, "telefone", "xx xxxxx xxxx"));
        resposta.addMeioComunicacao(new MeioComunicacao(2, "email", "xxx@xxx"));
        resposta.addMeioComunicacao(new MeioComunicacao("sem", "xxx@xxx"));

        // Serializar o objeto
        final String jsonTexto = jsonMapper.toJson(resposta);
        log.info("JSON Serializado: " + jsonTexto);
        redis.set("teste:teste", jsonTexto);

        // Desserializar o objeto
        final String valor = redis.get("teste:teste");
        log.info("Recuperado: " + valor);

        final RespostaJson jsonObjeto = jsonMapper.fromJson(valor, RespostaJson.class);
        return jsonObjeto;
    }

    public String mascaraEmail(String email) {

        final Matcher formatoEmailMatcher = REGEX_FORMATO_EMAIL.matcher(email);
        if (!formatoEmailMatcher.matches()) {
            throw new AplicacaoException("001", "Formato email invalido");
        }

        final Matcher emailMatcher = REGEX_MASCARA_EMAIL.matcher(email);        
        final String mascarado = emailMatcher.replaceAll("*");

        log.info(mascarado);

        return mascarado;
    }

}
