package org.boundary;

import org.entity.Map;
import org.entity.Partie;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

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

        Partie partie = this.pm.findById(id);
        if (partie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = partie.getToken().equals(token);

        if (!isTokenValide) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok(partie.oneToJson()).build();
        }
    }

    @GET
    @Path("{id}/photos")
    public Response getPartiePhotos(@PathParam("id") String id,
                                    @DefaultValue("") @QueryParam("token") String tokenParam,
                                    @DefaultValue("") @HeaderParam("token") String tokenHeader) {
        Partie partie = this.pm.findById(id);
        if (partie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = partie.getToken().equals(token);

        if (!isTokenValide) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            JsonArrayBuilder photos = Json.createArrayBuilder();
            partie.getPhotos().forEach((p) -> {
                JsonObject photo = p.buildJson();
                photos.add(photo);
            });
            return Response.ok(Json.createObjectBuilder()
                    .add("type", "collection")
                    .add("photos", photos)
                    .build()).build();
        }
    }

    @GET
    @Path("{id}/map")
    public Response getPartieMap(@PathParam("id") String id,
                                 @DefaultValue("") @QueryParam("token") String tokenParam,
                                 @DefaultValue("") @HeaderParam("x-lbs-token") String tokenHeader) {

        // on cherche la partie
        Partie partie = this.pm.findById(id);
        if (partie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // a-t-on un token ?
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = partie.getToken().equals(token);

        if (!isTokenValide) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok(partie.getMap().buildJson()).build();
        }
    }

    @POST
    public Response addPartie(JsonObject json) {

        String joueur, id_map, difficulte;
        Integer nbPhotos;
        Map map;

        try {
            nbPhotos = Integer.parseInt(json.getString("nbPhotos"));
            joueur = json.getString("joueur");
            id_map = json.getString("idMap");

            if (json.containsKey("difficulte")) {
                difficulte = json.getString("difficulte");
            } else {
                difficulte = "normal";
            }

            map = this.mm.findById(id_map);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Partie newPartie = this.pm.save(new Partie(nbPhotos, joueur, map, difficulte));
        URI uri = uriInfo.getAbsolutePathBuilder().path(newPartie.getId()).build();
        return Response.ok(newPartie.oneToJson()).build();
    }

    @PUT
    @Path("{id}")
    public Response addScore(@PathParam("id") String id, JsonObject json) {

        try {
            Partie partie = pm.findById(id);
            partie.setScore(Integer.parseInt(json.getString("score")));
            partie.setDifficulte(json.getString("difficulte"));
            pm.update(partie);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }


}
