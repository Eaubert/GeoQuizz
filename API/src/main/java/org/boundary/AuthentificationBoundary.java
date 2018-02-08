
package org.boundary;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.control.KeyManagement;
import org.control.PasswordManagement;
import org.entity.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("/authentification")
public class
AuthentificationBoundary {
    @Inject
    private KeyManagement keyManagement;

    @Inject
    private UtilisateurManager um;

    @Context
    private UriInfo uriInfo;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authentifieUtilisateur(JsonObject json) {
        try {
            String mdp = json.getString("mdp");
            String email = json.getString("email");
            // On authentifie l'utilisateur en utilisant les crédentails fournis
            authentifie(email, mdp);
            // On fournit un token
            String token = issueToken(email);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authentifie(String email, String motDePasse) throws Exception {
        Utilisateur utilisateur = um.findByEmail(email);

        // On lève une exception si les crédentials sont invalides
        if (email.equals(utilisateur.getEmail()) && BCrypt.checkpw(motDePasse, utilisateur.getMdp())) {
        } else {
            throw new NotAuthorizedException("Problème d'authentification");
        }
    }

    private String issueToken(String login) {
        Key key = keyManagement.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(30L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println(">>>> token/key : " + jwtToken + " -- " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
