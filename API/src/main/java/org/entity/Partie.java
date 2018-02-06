package org.entity;

import org.boundary.MapRessource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Partie.findAll",query="SELECT p FROM Partie p")
public class Partie implements Serializable{

    @Id
    private String id;

    @NotNull
    private String token;

    @NotNull
    private Integer nbPhotos;

    @NotNull
    private String statut;

    private Integer score;

    @NotNull
    private String joueur;

    @ManyToOne
    private Map map = new Map();

    @ManyToMany(mappedBy = "parties")
    private Set<Photo> photos = new HashSet<Photo>();

    public Partie() {
    }

    public Partie(Integer nbPhotos, String joueur, Map map) {
        this.nbPhotos = nbPhotos;
        this.statut = "en cours";
        this.score = 0;
        this.joueur = joueur;
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getNbPhotos() {
        return nbPhotos;
    }

    public void setNbPhotos(Integer nbPhotos) {
        this.nbPhotos = nbPhotos;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public JsonObject buildJson() {
        JsonObject self = Json.createObjectBuilder()
                .add("href", "/parties/" + this.id)
                .build();

        JsonObject linksMap = Json.createObjectBuilder()
                .add("href", "/parties/" + this.id + "/map")
                .build();

        JsonObject linksPhotos = Json.createObjectBuilder()
                .add("href", "parties/" + this.id + "/photos")
                .build();

        JsonObject links = Json.createObjectBuilder()
                .add("self", self)
                .add("photos", linksPhotos)
                .add("map", linksMap)
                .build();

        JsonObject details = Json.createObjectBuilder()
                .add("id", this.id)
                .add("token", this.token)
                .add("statut", this.statut)
                .add("score", this.score)
                .add("nbPhotos", this.nbPhotos)
                .add("idMap", this.getMap().getId())
                .build();

        return Json.createObjectBuilder()
                .add("partie", details)
                .add("links", links)
                .build();
    }

    public JsonObject partie2Json() {
        return Json.createObjectBuilder()
                .add("type", "resource")
                .add("partie", this.buildJson())
                .build();
    }

    public JsonObject partiePhotos2Json() {
        JsonArrayBuilder photos = Json.createArrayBuilder();
        this.photos.forEach((p) -> {
            JsonObject photo = p.buildJson();
            photos.add(photo);
        });

        return Json.createObjectBuilder()
                .add("type", "collection")
                .add("photos", photos)
                .build();
    }
}
