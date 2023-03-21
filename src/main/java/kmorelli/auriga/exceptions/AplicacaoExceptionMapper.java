package kmorelli.auriga.exceptions;

import java.util.logging.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AplicacaoExceptionMapper implements ExceptionMapper<AplicacaoException> {

    private static Logger log = Logger.getLogger(AplicacaoException.class.getCanonicalName());
    private static Jsonb jsonb = JsonbBuilder.create();

    @Override
    public Response toResponse(AplicacaoException exception) {
        
        Erro erro = new Erro();
        erro.code = exception.getCode();
        erro.message = exception.getMessage();

        String jsonErro;
        try {
            jsonErro = jsonb.toJson(erro);
        } catch (JsonbException e) {
            jsonErro = "{}";
        }

        Response resposta = Response.status(Status.BAD_REQUEST).entity(jsonErro).build();
        log.severe("Status: " + resposta.getStatus() + ", Resposta: " + resposta.getEntity().toString());
    
        return resposta;
    }
    
}
