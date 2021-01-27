package br.auriga;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-resteasy")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/teste")
    @Produces(MediaType.TEXT_PLAIN)
    public String teste() {
    	return "Testando";
    }

    @GET
    @Path("/teste/{nome}")
    @Produces(MediaType.TEXT_PLAIN)
    public String teste(@PathParam("nome") String nome) {
    	return "Voce esta testando por " + nome + " ?";
    }

}


