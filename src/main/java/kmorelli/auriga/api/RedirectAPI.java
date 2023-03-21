package kmorelli.auriga.api;

import java.net.URI;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import kmorelli.auriga.services.cache.CacheToken;

@RequestScoped
@Path("/rapi")
public class RedirectAPI {

    @Inject
    CacheToken base;

    @Context
    HttpServerRequest request;

    @GET
    @Path("/testeredirect")
    public CompletionStage<Response> testeRedirect() {

        String prefix = request.absoluteURI().replace(request.path(), "");

        Uni<Response> resp = base.createToken()
                .onItem().transform(
                        code -> Response.status(Status.FOUND)
                                .location(URI.create(prefix + "/rapi/token?code=" + code)).build());

        return resp.subscribeAsCompletionStage();
    }

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> token(@QueryParam("code") String code) {
        return base.getToken(code)
                .onItem().transform(
                        token -> Response.ok(token).build());
    }
}