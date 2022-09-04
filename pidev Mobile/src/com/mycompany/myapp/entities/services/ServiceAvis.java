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
import com.mycompany.myapp.entities.Avis;

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
public class ServiceAvis {

    public ArrayList<Avis> aviss;
    
    public static ServiceAvis instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceAvis() {
         req = new ConnectionRequest();
    }

    public static ServiceAvis getInstance() {
        if (instance == null) {
            instance = new ServiceAvis();
        }
        return instance;
    }
    


    public ArrayList<Avis> parseAviss(String jsonText){
                try {

                    System.out.println(jsonText);
            aviss=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Avis a = new Avis();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                String test=obj.get("user").toString();
                test=test.substring((test).indexOf("id=")+3 ,(test).indexOf(", firstname"));
                float iduser = Float.parseFloat(test);

                a.setIduser((int) iduser);
                test=obj.get("hotel").toString();
                test=test.substring((test).indexOf("id=")+3 ,(test).indexOf(", nom"));
                float idhotel = Float.parseFloat(test);
                a.setIdhotel((int) idhotel); 
                a.setRate(Float.parseFloat(obj.get("rate").toString()));
                        

                aviss.add(a);


            }
        } catch (IOException ex) {
            
        }
        return aviss;
    }
    public ArrayList<Avis> getAllAviss(){
        String url = Statics.BASE_URL+"avis/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                aviss = parseAviss(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return aviss;
    }
    public ArrayList<Avis> getHotelAviss(int id){
        String url = Statics.BASE_URL+"avis/mobile/affhotel?id="+id;
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                aviss = parseAviss(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return aviss;
    }


    public boolean addAvis(Avis u) {
        String url = Statics.BASE_URL + "avis/mobile/new?rate="+u.getRate()+"&iduser="+u.getIduser()+"&idhotel="+u.getIdhotel();//création de l'URL
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

        public boolean editAvis(Avis u) {
        String url = Statics.BASE_URL + "avis/mobile/edit?id="+u.getId()+"&rate="+u.getRate();//création de l'URL
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

    public boolean deleteAvis(int id) {
        String url = Statics.BASE_URL + "avis/mobile/del?id=" + id;
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
