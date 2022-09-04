/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reclamation;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author msi
 */
public class ServiceReclamation {

    public ArrayList<Reclamation> reclamations;
    
    public static ServiceReclamation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceReclamation() {
         req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }
    


    public ArrayList<Reclamation> parseReclamations(String jsonText){
                try {

                    System.out.println(jsonText);
            reclamations=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Reclamation a = new Reclamation();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                                
                String test=obj.get("user").toString();
                test=test.substring((test).indexOf("id=")+3 ,(test).indexOf(", email"));

                float iduser = Float.parseFloat(test);
                a.setId((int) id);
                a.setIduser((int) iduser);
                a.setType(obj.get("type").toString());
                a.setDescription(obj.get("description").toString());
                a.setSujet(obj.get("sujet").toString());
                a.setEtat(obj.get("etat").toString());
                a.setImage(obj.get("image").toString());
                a.setMontant(Float.parseFloat(obj.get("Montant").toString()));

                reclamations.add(a);
            }
        } catch (IOException ex) {
            
        }
        return reclamations;
    }
    public ArrayList<Reclamation> getAllReclamations(){
        String url = Statics.BASE_URL+"reclamation/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }


    public boolean addReclamation(Reclamation u) {
        String url = Statics.BASE_URL + "reclamation/mobile/new?iduser="+u.getIduser()+"&type="+u.getType()+"&description="+u.getDescription()+"&sujet="+u.getSujet()+"&image="+u.getImage()+"&montant="+u.getMontant();//création de l'URL
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

        public boolean editReclamation(Reclamation u) {
        String url = Statics.BASE_URL + "reclamation/mobile/edit?id="+u.getId()+"&etat="+u.getEtat()+"&type="+u.getType()+"&description="+u.getDescription()+"&sujet="+u.getSujet()+"&image="+u.getImage()+"&montant="+u.getMontant();//création de l'URL
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteReclamation(int id) {
        String url = Statics.BASE_URL + "reclamation/mobile/del?id=" + id;
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
