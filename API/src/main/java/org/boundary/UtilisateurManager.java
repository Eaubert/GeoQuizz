package org.boundary;

import org.entity.Map;
import org.entity.Utilisateur;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class UtilisateurManager {

    @PersistenceContext
    EntityManager em;

    public Utilisateur findByEmail(String email) {
        try {
            return this.em.find(Utilisateur.class, email);
        } catch (NotFoundException nfe) {
            return null;
        }
    }

    public List<Utilisateur> findAll() {
        Query q = this.em.createNamedQuery("Utilisateur.findAll", Map.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Utilisateur save(Utilisateur u) {
        return this.em.merge(u);
    }

    public void delete(String email) {
        try {
            Utilisateur ref = this.em.getReference(Utilisateur.class, email);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
        }
    }

}
