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
import com.mycompany.myapp.entities.Offre;

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
public class ServiceOffre {

    public ArrayList<Offre> offres;
    
    public static ServiceOffre instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceOffre() {
         req = new ConnectionRequest();
    }

    public static ServiceOffre getInstance() {
        if (instance == null) {
            instance = new ServiceOffre();
        }
        return instance;
    }
    


    public ArrayList<Offre> parseOffres(String jsonText){
                try {

                    System.out.println(jsonText);
            offres=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Offre a = new Offre();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setDestination(obj.get("destination").toString());
                a.setPrix(Float.parseFloat(obj.get("prix").toString()));
                        
                try {  
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateDebut").toString());
                            Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateFin").toString());
                            a.setDatedeb(date1);
                            a.setDatefin(date2);

                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }
                               
                offres.add(a);


            }
        } catch (IOException ex) {
            
        }
        return offres;
    }
    public ArrayList<Offre> getAllOffres(){
        String url = Statics.BASE_URL+"offre/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                offres = parseOffres(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return offres;
    }


    public boolean addOffre(Offre u) {
        String url = Statics.BASE_URL + "offre/mobile/new?destination="+u.getDestination()+"&datedeb="+u.getDatedeb()+"&datefin="+u.getDatefin()+"&prix="+u.getPrix();//création de l'URL
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

        public boolean editOffre(Offre u) {
        String url = Statics.BASE_URL + "offre/mobile/edit?id="+u.getId()+"&destination="+u.getDestination()+"&datedeb="+u.getDatedeb()+"&datefin="+u.getDatefin()+"&prix="+u.getPrix();//création de l'URL
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

    public boolean deleteOffre(int id) {
        String url = Statics.BASE_URL + "offre/mobile/del?id=" + id;
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
