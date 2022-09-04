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
public class Offre {
    
    private int id;
    private Date datedeb;
    private Date datefin;
    private float prix;
    private String destination;
    

    public Offre() {
    }

    public Offre(int id, Date datedeb, Date datefin, float prix, String destination) {
        this.id = id;
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.prix = prix;
        this.destination = destination;
    }

    public Offre(Date datedeb, Date datefin, float prix, String destination) {
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.prix = prix;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(Date datedeb) {
        this.datedeb = datedeb;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    
}
