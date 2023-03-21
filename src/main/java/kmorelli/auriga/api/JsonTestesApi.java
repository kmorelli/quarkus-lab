package kmorelli.auriga.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import kmorelli.auriga.services.json.JsonTesteService;

@RequestScoped
@Path("/json")
public class JsonTestesApi {
    
    @Inject
    JsonTesteService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/testar")
    public Response testarJson() {

        return Response.ok(service.testarJson()).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mascarar")
    public Response mascarar(String email) {

        return Response.ok(service.mascaraEmail(email)).build();

    }

}
