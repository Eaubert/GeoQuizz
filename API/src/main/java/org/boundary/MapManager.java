package org.boundary;

import org.entity.Map;
import org.entity.Photo;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Stateless
public class MapManager {

    @PersistenceContext
    EntityManager em;

    public Map findById(String id) {
        try {
            Map m = this.em.find(Map.class, id);
            return m;
        } catch (NotFoundException nfe) {
            return null;
        }
    }

    public Map findByName(String ville) {
        return this.em.find(Map.class, ville);
    }

    public List<Map> findAll() {
        Query q = this.em.createNamedQuery("Map.findAll", Map.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Map save(Map m) {
        return this.em.merge(m);
    }

    public void delete(String id) {
        try {
            Map ref = this.em.getReference(Map.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
        }
    }

}
