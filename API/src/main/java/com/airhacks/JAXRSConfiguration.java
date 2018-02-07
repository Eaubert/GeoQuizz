package com.airhacks;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(org.cors.CORSRequestFilter.class);
        classes.add(org.cors.CORSResponseFilter.class);
        classes.add(org.boundary.AuthentificationBoundary.class);
        classes.add(org.boundary.UtilisateurRessource.class);
        classes.add(org.boundary.MapRessource.class);
        classes.add(org.boundary.PhotoRessource.class);
        classes.add(org.boundary.PartieRessource.class);

        return classes;
    }
}
