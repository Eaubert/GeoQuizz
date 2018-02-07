package org.boundary;

import org.boundary.exception.MapNotFound;
import org.entity.Map;
import org.entity.Partie;
import org.entity.Photo;
import org.provider.Secured;

import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
        return Optional.ofNullable(this.mm.findById(id))
                .map(m -> Response.ok((m.map2Json())).build())
                .orElseThrow(() -> new MapNotFound("Ressource non disponible" + uriInfo.getPath()));
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
    public Response getMapPhotos(@PathParam("id") String id, @Context UriInfo uriInfo) throws Throwable {
        return Optional.ofNullable(this.mm.findById(id))
                .map(m -> Response.ok((m.photos2Json())).build())
                .orElseThrow(() -> new MapNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @POST
    @Secured
    public Response addMap(@Valid Map m, @Context UriInfo uriInfo) {
        m.setId(UUID.randomUUID().toString());
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + this.mm.save(m).getId()).build();
        return Response.created(uri).build();
    }


}
