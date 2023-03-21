package kmorelli.auriga.services.extension;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import kmorelli.auriga.exceptions.ExtensionResponseErrorMapper;

@Path("/extensionss")
@RegisterRestClient(configKey = "extensions-api")
@RegisterProvider(ExtensionClientFilter.class)
@RegisterProvider(ExtensionResponseErrorMapper.class)
public interface ExtensionsService {
    
    @GET
    Set<Extension> getById(@QueryParam("id") String id);

    @GET
    CompletionStage<Set<Extension>> getByIdAsync(@QueryParam("id") String id, @HeaderParam("teste-header") String headerValue);
}
