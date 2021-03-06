package org.boundary;

import org.entity.Photo;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@Stateless
public class PhotoManager {

    @PersistenceContext
    EntityManager em;

    public Photo findById(String id) {
        try {
            Photo p = this.em.find(Photo.class, id);
            return p;
        } catch (NotFoundException nfe) {
            return null;
        }
    }

    public List<Photo> findAll() {
        Query q = this.em.createNamedQuery("Photo.findAll", Photo.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Photo save(Photo p) {
        p.setId(UUID.randomUUID().toString());
        return this.em.merge(p);
    }

    public void delete(String id) {
        try {
            Photo ref = this.em.getReference(Photo.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
        }
    }

}
