package org.boundary;

import org.boundary.exception.PartieNotFound;
import org.entity.Map;
import org.entity.Partie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.provider.Secured;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Stateless
@Path("parties")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PartieRessource {

    @Inject
    PartieManager pm;

    @Inject
    MapManager mm;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{id}")
    public Response getPartieById(@PathParam("id") String id,
                                   @DefaultValue("") @QueryParam("token") String tokenParam,
                                   @DefaultValue("") @HeaderParam("x-lbs-token") String tokenHeader) {

        // on cherche la partie
        Partie partie = this.pm.findById(id);
        if(partie == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // a-t-on un token ?
        if(tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = partie.getToken().equals(token);

        if(!isTokenValide) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok(partie.partie2Json()).build();
        }

    }

    @GET
    @Path("{id}/photos")
    public Response getPartiePhotos(@PathParam("id") String id,
                                  @DefaultValue("") @QueryParam("token") String tokenParam,
                                  @DefaultValue("") @HeaderParam("x-lbs-token") String tokenHeader) {

        // on cherche la partie
        Partie partie = this.pm.findById(id);
        if(partie == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // a-t-on un token ?
        if(tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = partie.getToken().equals(token);

        if(!isTokenValide) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok(partie.partiePhotos2Json()).build();
        }

    }

    @GET
    @Path("{id}/map")
    public Response getPartieMap(@PathParam("id") String id,
                                    @DefaultValue("") @QueryParam("token") String tokenParam,
                                    @DefaultValue("") @HeaderParam("x-lbs-token") String tokenHeader) {

        // on cherche la partie
        Partie partie = this.pm.findById(id);
        if(partie == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // a-t-on un token ?
        if(tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = partie.getToken().equals(token);

        if(!isTokenValide) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            Map m = partie.getMap();
            return Response.ok(Json.createObjectBuilder()
                    .add("map", m.toJson())
                    .add("links", m.getLinks())
                    .build()).build();
        }
        ;
    }

    @POST
    public Response addPartie(JsonObject json) {

        String joueur,id_map;
        Integer nbPhotos;
        Map map;

        try{
            nbPhotos = Integer.parseInt(json.getString("nbPhotos"));
            joueur = json.getString("joueur");
            id_map = json.getString("idMap");

            map = this.mm.findById(id_map);
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Partie newPartie = this.pm.save(new Partie(nbPhotos,joueur,map));
        URI uri = uriInfo.getAbsolutePathBuilder().path(newPartie.getId()).build();
        return Response.ok(newPartie.partie2Json()).build();
    }



}
