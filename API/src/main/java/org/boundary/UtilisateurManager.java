package org.boundary;

import org.entity.Map;
import org.entity.Photo;
import org.entity.Utilisateur;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Stateless
public class UtilisateurManager {

    @PersistenceContext
    EntityManager em;

    public Utilisateur findByEmail(String email) {
        return this.em.find(Utilisateur.class, email);
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
        } catch (EntityNotFoundException e) { }
    }

}
