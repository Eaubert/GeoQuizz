package org.boundary;

import org.boundary.exception.PhotoNotFound;
import org.entity.Map;
import org.entity.Photo;
import org.omg.CosNaming.NamingContextPackage.NotFound;
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
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Stateless
@Path("photos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoRessource {

    @Inject
    PhotoManager pm;

    @Inject
    MapManager mm;

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
    public Response getOnePhoto(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Photo p = this.pm.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(p.photo2Json()).build();
    }

    @GET
    @Path("{id}/map")
    public Response getPhotoMap(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Photo p = this.pm.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(p.getMap().map2Json()).build();
    }


    @POST
    @Secured
    public Response addPhoto(@Valid Photo p,
                             @DefaultValue("") @QueryParam("idMap") String idMap,
                             @Context UriInfo uriInfo) {
        Map m = mm.findById(idMap);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        p.setMap(m);
        p.setId(UUID.randomUUID().toString());
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + this.pm.save(p).getId()).build();
        return Response.created(uri).build();
    }


    private JsonArray getPhotosList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.pm.findAll().forEach((p) -> {
            jab.add(p.buildJson());
        });
        return jab.build();
    }

    @DELETE
    @Secured
    @Path("{id}")
    @Produces("application/json")
    public Response methodeSecurisee(@PathParam("id") Long id) {
        // La méthode est annotée avec @Secured
        // Le filtre est exécuté
        // On doit avoir un token valide
        String str = "Sécurisé";
        JsonObject jsonResult = Json.createObjectBuilder().
                add("status", str).build();
        return Response.ok().entity(jsonResult).build();
    }

}
