/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author msi
 */
public class Avis {
    private int id;
    private int idhotel;
    private int iduser;
    private float rate;

    public Avis(int id, int idhotel, int iduser, float rate) {
        this.id = id;
        this.idhotel = idhotel;
        this.iduser = iduser;
        this.rate = rate;
    }

    public Avis(int idhotel, int iduser, float rate) {
        this.idhotel = idhotel;
        this.iduser = iduser;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdhotel() {
        return idhotel;
    }

    public void setIdhotel(int idhotel) {
        this.idhotel = idhotel;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Avis() {
    }
    
    
    
}
