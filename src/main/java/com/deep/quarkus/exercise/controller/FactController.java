package com.deep.quarkus.exercise.controller;

import com.deep.quarkus.exercise.handler.JsonBodyHandler;
import com.deep.quarkus.exercise.exception.FactNotFoundException;
import com.deep.quarkus.exercise.model.CachedFact;
import com.deep.quarkus.exercise.model.UselessFact;
import com.deep.quarkus.exercise.service.FactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.logging.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Path("/v1/facts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FactController {
    private static final Logger log = Logger.getLogger(FactController.class);
    private static final String url = "https://uselessfacts.jsph.pl/random.json?language=en";

    private final HttpClient client;
    private final HttpRequest request;
    private final URI uri;
    private final ObjectMapper objectMapper;

    @Inject
    FactService factService;

    FactController() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        this.uri = URI.create(url);
        this.request = HttpRequest.newBuilder(uri).build();
        this.objectMapper = new ObjectMapper();
    }

    @POST
    @Operation(summary = "Fetches a random fact from the Useless Facts API, stores it, and returns a shortened URL.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Get random fact"),
            @APIResponse(responseCode = "404", description = "Fact not found")})
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response fetchFact() throws Exception{
        Jsonb jsonb = JsonbBuilder.create();
        UselessFact factResponse = client.send(request,
                JsonBodyHandler.jsonBodyHandler(jsonb, UselessFact.class)).body();

        if(factResponse != null) {
            String shortenedUrl = factService.generateShortenedUrl(factResponse);
            return Response.ok().entity(shortenedUrl).build();
        }
        throw new FactNotFoundException("Fact not found!");
    }

    @GET
    @Path("/{shortenedUrl}")
    @Operation(summary = "Returns the cached fact and increments the access count.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Return single cached fact"),
            @APIResponse(responseCode = "404", description = "Fact not found")})
    @PermitAll
    public Response getMeFact(@PathParam("shortenedUrl") String url) {
        CachedFact fact = factService.getFact(url);
        if(fact != null){
            return Response.ok(fact).build();
        }
        throw new FactNotFoundException("Fact not found!");
    }

    @GET
    @Path("/getAllFacts")
    @Operation(summary = "Returns all cached facts and does not increment the access count.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Return all cached facts")})
    @PermitAll
    public Response fetchAllFacts() {
        return Response.ok(factService.getAllFacts()).build();
    }

    @GET
    @Path("/{shortenedUrl}/redirect")
    @Operation(summary = "Redirects to the original fact and increments the access count.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Redirect to original fact"),
            @APIResponse(responseCode = "404", description = "Fact not found")})
    @PermitAll
    public Response redirect(@PathParam("shortenedUrl") String url) {
        CachedFact fact = factService.redirectToOriginal(url);
        if(fact != null) {
            return Response.temporaryRedirect(URI.create(fact.getLink())).build();
        }
        throw new FactNotFoundException("Fact not found!");
    }

    @GET
    @Path("/admin/statistics")
    @Operation(summary = "Provides access statistics for all shortened URLs.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Get access statistics")})
    @RolesAllowed("admin")
    public Response statistics(@Context SecurityContext sec) {
            Map<String, Integer> statistics = new HashMap<>();
            Map<String, CachedFact> facts = factService.getAllFacts();
            facts.forEach((key, value) -> statistics.put(key, value.getAccessCount()));

            return Response.ok(statistics).build();
    }
}
