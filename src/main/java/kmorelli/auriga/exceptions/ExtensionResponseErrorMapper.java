package kmorelli.auriga.exceptions;

import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;



@RequestScoped
public class ExtensionResponseErrorMapper implements ResponseExceptionMapper<ExtensionException> {

    private Logger log = Logger.getLogger(ExtensionResponseErrorMapper.class.getName());

    @Override
    public ExtensionException toThrowable(Response response) {

        InputStream body = (InputStream)response.getEntity();
        JsonReader jsonReader = Json.createReader(body);
        JsonObject jsonObject = jsonReader.readObject();

        log.info("Erro json: " + jsonObject);
        log.info("Extraindo o path: " + jsonObject.getString("origem"));

        final StatusType statusInfo = response.getStatusInfo();
        return new ExtensionException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
    }

}
