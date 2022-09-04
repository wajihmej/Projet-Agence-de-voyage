/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;
import java.util.Date;

/**
 *
 * @author msi
 */
public class Reclamation {
    
    private int id;
    private int iduser;
    private String type;
    private String description;
    private String sujet;
    private String etat;
    private String image;
    private float montant;
    

    public Reclamation() {
    }

    public Reclamation(int id, int iduser, String type, String description, String sujet, String etat, String image, float montant) {
        this.id = id;
        this.iduser = iduser;
        this.type = type;
        this.description = description;
        this.sujet = sujet;
        this.etat = etat;
        this.image = image;
        this.montant = montant;
    }

    public Reclamation(int iduser, String type, String description, String sujet, String etat, String image, float montant) {
        this.iduser = iduser;
        this.type = type;
        this.description = description;
        this.sujet = sujet;
        this.etat = etat;
        this.image = image;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }


    
}
