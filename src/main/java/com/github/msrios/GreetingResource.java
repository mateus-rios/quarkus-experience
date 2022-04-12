package com.github.msrios;

import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @RestPath
    String texto;

    @GET
    @Path("mutiny/{texto}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> mutiny() {
        return Uni.createFrom().item(texto).onItem().invoke(item -> {
            if ("error".equals(texto)) throw new IllegalArgumentException();
        }).onFailure().recoverWithItem(f -> {
            System.out.println(f);
            return "ERRINHO";
        }).map(String::toUpperCase);
    }
}