package org.boundary;

import org.control.RandomToken;
import org.entity.Partie;
import org.entity.Photo;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Stateless
public class PartieManager {

    @PersistenceContext
    EntityManager em;

    public List<Partie> findAll() {
        Query q = this.em.createNamedQuery("Partie.findAll", Partie.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Partie findById(String id) {
        try{
            return this.em.find(Partie.class, id);
        }catch(Exception e){
            return null;
        }
    }
  
    public Partie save(Partie p) {
        p.setId(UUID.randomUUID().toString());

        RandomToken rt = new RandomToken();
        String token = rt.randomString(64);
        p.setToken(token);
        //remplir random photos
        List listPhotos = em.createQuery(
                "SELECT p FROM Photo p WHERE map_id = :idmap ORDER BY RANDOM()")
                .setParameter("idmap", p.getMap().getId())
                .setMaxResults( p.getNbPhotos())
                .getResultList();
        Set<Photo> set = new HashSet<Photo>(listPhotos);
        p.setPhotos(set);

        return this.em.merge(p);
    }

    public void delete(String id) {
        try {
            Partie ref = this.em.getReference(Partie.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) { }
    }
}
