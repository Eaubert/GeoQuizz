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


    @POST
    @Secured
    public Response addPhotos(JsonObject json) {
        String id,url;
        Float latitude,longitude;
        Integer nbPhotos;

        try{
            nbPhotos = Integer.parseInt(json.getString("nbPhotos"));

            JsonArray photos = json.getJsonArray("photos");
            for(int i=0;i<nbPhotos; i++){
                JsonObject photo = photos.getJsonObject(i);
                url = photo.getString("url");
                id = photo.getString("id");
                latitude = Float.parseFloat(photo.getString("latitude"));
                longitude = Float.parseFloat(photo.getString("longitude"));

                Map map = mm.findById(id);
                Photo p = new Photo(url,longitude,latitude, map);
                map.addPhoto(p);
                pm.save(p);
            }

        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }


    private JsonArray getPhotosList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.pm.findAll().forEach((p) -> {
            jab.add(p.buildJson());
        });
        return jab.build();
    }

}
