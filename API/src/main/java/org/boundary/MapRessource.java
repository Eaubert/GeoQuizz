package org.boundary;

import org.entity.Map;
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
import java.util.UUID;

@Stateless
@Path("maps")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MapRessource {

    @Inject
    MapManager mm;

    @Context
    UriInfo uriInfo;

    @GET
    public Response getMaps() {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("maps", this.getMapList())
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    public Response getMapById(@PathParam("id") String id, @Context UriInfo uriInfo) throws Throwable {
        Map m = this.mm.findById(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(Json.createObjectBuilder()
                .add("type", "resource")
                .add("map", m.toJson())
                .add("links", m.getLinks())
                .build()).build();
    }

    private JsonArray getMapList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.mm.findAll().forEach((m) -> {
            jab.add(m.buildJson());
        });
        return jab.build();
    }

    @GET
    @Path("{id}/photos")
    public Response getMapPhotos(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Map m = this.mm.findById(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JsonArrayBuilder photos = Json.createArrayBuilder();
        m.getListPhotos().forEach((p) -> {
            JsonObject photo = p.buildJson();
            photos.add(photo);
        });
        return Response.ok(Json.createObjectBuilder()
                .add("type", "collection")
                .add("photos", photos)
                .build()).build();
    }

    @GET
    @Path("{id}/parties")
    public Response getMapParties(@PathParam("id") String id, @Context UriInfo uriInfo){
        Map m = this.mm.findById(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JsonArrayBuilder parties = Json.createArrayBuilder();
        m.getListPartie().forEach((p) -> {
            JsonObject partie = p.buildJson();
            parties.add(partie);
        });
        return Response.ok(Json.createObjectBuilder()
                .add("type", "collection")
                .add("parties", parties)
                .build()).build();
    }

    @POST
    @Secured
    public Response addMap(@Valid Map m, @Context UriInfo uriInfo) {
        m.setVille(m.getVille().substring(0, 1).toUpperCase() + m.getVille().substring(1));
        Map verifMap = mm.findByName(m.getVille());

        if (verifMap != null) {
            m = verifMap;
        } else {
            m.setId(UUID.randomUUID().toString());
            this.mm.save(m);
        }

        return Response.ok(Json.createObjectBuilder()
                .add("map", m.toJson())
                .add("links", m.getLinks())
                .build()).build();

        /*URI uri = uriInfo.getAbsolutePathBuilder().path("/" + this.mm.save(m).getId()).build();
        return Response.created(uri).build();*/
    }
}
