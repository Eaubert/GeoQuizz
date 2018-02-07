package org.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
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
@NamedQuery(name="Photo.findAll",query="SELECT p FROM Photo p")
public class Photo implements Serializable{

    @Id
    private String id;

    @NotNull
    private String url;
    @NotNull
    private Float longitude;
    @NotNull
    private Float latitude;

    @ManyToOne
    private Map map = new Map();

    @ManyToMany
    private Set<Partie> parties = new HashSet<>();

    public Photo() {
    }

    public Photo(String url, Float longitude, Float latitude, Map map) {
        this.url = url;
        this.longitude = longitude;
        this.latitude = latitude;
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Set<Partie> getParties() {
        return parties;
    }

    public void setParties(Set<Partie> parties) {
        this.parties = parties;
    }

    public void addParties(Partie p){
        this.parties.add(p);
    }

    public JsonObject buildJson() {
        return Json.createObjectBuilder()
                .add("photo", this.toJson())
                .add("links", this.getLinks())
                .build();
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("url", this.url)
                .add("longitude", this.longitude)
                .add("latitude", this.latitude)
                .add("idMap", this.map.getId())
                .build();
    }

    public JsonObject getLinks() {
        return Json.createObjectBuilder()
                .add("self", Json.createObjectBuilder()
                        .add("href", "/photos/" + this.getId())
                        .build())
                .add("map", Json.createObjectBuilder()
                        .add("href", "/photos/" + this.getId() + "/map")
                        .build())
                .build();
    }
}
