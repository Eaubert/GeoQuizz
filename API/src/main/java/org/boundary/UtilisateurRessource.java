package org.boundary;

import org.boundary.exception.PhotoNotFound;
import org.control.PasswordManagement;
import org.entity.Map;
import org.entity.Partie;
import org.entity.Photo;
import org.entity.Utilisateur;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

import static org.control.PasswordManagement.digestPassword;

@Stateless
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurRessource {

    @Inject
    UtilisateurManager um;

    @Context
    UriInfo uriInfo;

    @POST
    public Response addUtilisateur(JsonObject json) {

        String email,pseudo,mdp;
        try{
        email = json.getString("email");
        pseudo = json.getString("pseudo");
        mdp = json.getString("mdp");

    }catch(Exception e){
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

        String mdpHash = digestPassword(mdp);

       Utilisateur newUtilisateur = this.um.save(new Utilisateur(email,pseudo,mdpHash));
        return Response.ok(newUtilisateur.utilisateur2Json()).build();
    }


}
