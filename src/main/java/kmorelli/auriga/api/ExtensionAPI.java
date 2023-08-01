package kmorelli.auriga.api;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import kmorelli.auriga.exceptions.AplicacaoException;
import kmorelli.auriga.exceptions.AplicacaoExceptionMapper;
import kmorelli.auriga.exceptions.ExtensionException;
import kmorelli.auriga.services.extension.AutenticarExtension;
import kmorelli.auriga.services.extension.Extension;
import kmorelli.auriga.services.extension.ExtensionsService;

@Path("/extension")
@RegisterProvider(AplicacaoExceptionMapper.class)
public class ExtensionAPI {

    @Inject
    AutenticarExtension autenticarExtension;

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    @GET
    @Path("/id/{id}")
    public Set<Extension> id(@PathParam("id") String id) {
        try {
            return extensionsService.getById(id);
        } catch (ExtensionException e) {
            throw new AplicacaoException(Integer.toString(e.getCode()), e.getMessage());
        }
    }

    @GET
    @Path("/asyncid/{id}")
    public CompletionStage<Set<Extension>> asyncId(@PathParam("id") String id) {
        try {
            return extensionsService.getByIdAsync(id, autenticarExtension.getUuid());
        } catch (ExtensionException e) {
            throw new AplicacaoException(Integer.toString(e.getCode()), e.getMessage());
        }

    }

}
