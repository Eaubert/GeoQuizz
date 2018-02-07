package org.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = "Map.findAll", query = "SELECT m FROM Map m")
public class Map implements Serializable {

    @Id
    private String id;

    @NotNull
    private String ville;

    @NotNull
    private Float longitude;

    @NotNull
    private Float latitude;

    private Float distance;

    @OneToMany(mappedBy = "map")
    private Set<Photo> photos = new HashSet<Photo>();

    @OneToMany(mappedBy = "map")
    private Set<Partie> parties = new HashSet<Partie>();

    public Map() {
    }

    public Map(String ville, Float longitude, Float latitude, Float distance) {
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Set<Photo> getListPhotos() {
        return photos;
    }

    public void setListPhotos(Set<Photo> listPhotos) {
        this.photos = listPhotos;
    }

    public Set<Partie> getListPartie() {
        return parties;
    }

    public void setListPartie(Set<Partie> listPartie) {
        this.parties = listPartie;
    }

    public void addPhoto(Photo p) {
        this.photos.add(p);
    }

    public JsonObject buildJson() {
        return Json.createObjectBuilder()
                .add("map", this.toJson())
                .add("links", this.getLinks())
                .build();
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("ville", this.ville)
                .add("longitude", this.longitude)
                .add("latitude", this.latitude)
                .add("distance", this.distance)
                .build();
    }

    public JsonObject getLinks() {
        return Json.createObjectBuilder()
                .add("self", Json.createObjectBuilder()
                        .add("href", "/maps/" + this.getId())
                        .build())
                .add("photos", Json.createObjectBuilder()
                        .add("href", "/maps/" + this.getId() + "/photos")
                        .build())
                .build();
    }
}
