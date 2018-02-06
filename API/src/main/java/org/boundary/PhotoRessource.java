package org.boundary;

import org.boundary.exception.PhotoNotFound;
import org.entity.Map;
import org.entity.Photo;
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
import java.util.Optional;
import java.util.Set;

@Stateless
@Path("photos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoRessource {

    @Inject
    PhotoManager pm;

    @Context
    UriInfo uriInfo;

    @GET
    public Response getPhotos() {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("photos", this.getPhotosList())
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    public Response getOnePhoto(@PathParam("id") String id, @Context UriInfo uriInfo) throws Throwable {
        return Optional.ofNullable(this.pm.findById(id))
                .map(p -> Response.ok(p.photo2Json()).build())
                .orElseThrow(() -> new PhotoNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @GET
    @Path("{id}/map")
    public Response getPhotoMap(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Photo p = this.pm.findById(id);
        Map map = p.getMap();

        return Response.ok(map.map2Json()).build();
    }


    private JsonArray getPhotosList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.pm.findAll().forEach((p) -> {
            jab.add(p.buildJson());
        });
        return jab.build();
    }

}
