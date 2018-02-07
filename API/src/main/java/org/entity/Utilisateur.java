package org.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {

    @Id
    private String email;

    @NotNull
    private String mdp;

    @NotNull
    private String pseudo;

    private Integer score;

    public Utilisateur() {
    }

    public Utilisateur(String email, String pseudo, String mdp) {
        this.email = email;
        this.mdp = mdp;
        this.pseudo = pseudo;
        this.score = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("pseudo", this.pseudo)
                .add("mail", this.email)
                .add("score", this.score)
                .build();
    }

    public JsonObject getLinks() {
        return Json.createObjectBuilder()
                .add("href", "/users/" + this.email)
                .build();
    }
}
