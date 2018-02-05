package org.lpro.boundary;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.lpro.control.RandomToken;
import org.lpro.entity.Commande;

@Stateless
@Transactional
public class CommandeRessource {

    @PersistenceContext
    EntityManager em;

    public Commande findById(String id) {
        return this.em.find(Commande.class, id);
    }


    @GET
    @Path("commandes")
    public List<Commande> findAll() {
        Query q = this.em.createQuery("SELECT c FROM Commande c");
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }
    public Commande save(Commande c) {
        // enregistrer la commande
        //ajout du token
        RandomToken rt = new RandomToken();
        String token = rt.randomString(64);
        c.setToken(token);
        //ajout de UUID
        c.setId(UUID.randomUUID().toString());
        //persister la commande dans la BD
        return this.em.merge(c);
    }

    public void update(Commande c){
        this.em.merge(c);
    }



}
