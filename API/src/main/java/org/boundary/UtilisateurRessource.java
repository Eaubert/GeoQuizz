package org.boundary;

import org.entity.Utilisateur;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

        String email, pseudo, mdp;
        try {
            email = json.getString("email");
            pseudo = json.getString("pseudo");
            mdp = json.getString("mdp");

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String mdpHash = digestPassword(mdp);

        Utilisateur newUtilisateur = this.um.save(new Utilisateur(email, pseudo, mdpHash));
        return Response.ok(Json.createObjectBuilder()
                .add("type", "resource")
                .add("utilisateur", newUtilisateur.toJson())
                .add("links", newUtilisateur.getLinks())
                .build())
                .build();
    }


}
