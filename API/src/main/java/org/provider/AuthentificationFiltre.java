
package org.provider;

import io.jsonwebtoken.Jwts;
import org.control.KeyManagement;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

@Secured
@Provider
public class AuthentificationFiltre implements ContainerRequestFilter {

    @Inject
    private KeyManagement keyManagement;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Récupère le header HTTP à partir de la requête
System.out.println("azertyuiop");
        String authHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // On vérifie que le header Authorization est présent et formatté correctement
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Probleme header autorisation");
        }

        // On extrait le token, et on vérifie qu'il est valide
        String token = authHeader.substring("Bearer".length()).trim();

        try {
            // Valide le token...
            Key key = keyManagement.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            System.out.println(">>>> token valide : " + token);
        } catch (Exception e) {
            // ... ou pas
            System.out.println(">>>> token invalide : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}
