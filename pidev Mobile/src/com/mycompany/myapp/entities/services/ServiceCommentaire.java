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
import com.mycompany.myapp.entities.Commentaire;

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
public class ServiceCommentaire {

    public ArrayList<Commentaire> commentaires;
    
    public static ServiceCommentaire instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceCommentaire() {
         req = new ConnectionRequest();
    }

    public static ServiceCommentaire getInstance() {
        if (instance == null) {
            instance = new ServiceCommentaire();
        }
        return instance;
    }
    


    public ArrayList<Commentaire> parseCommentaires(String jsonText){
                try {

                    System.out.println(jsonText);
            commentaires=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Commentaire a = new Commentaire();
                                
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
                try {  
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("createdAt").toString());
                            a.setDatecreation(date1);

                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }
                if(obj.get("isPublished").toString().equals("true"))
                {
                    a.setIsPublished(true);
                }
                else
                {
                    a.setIsPublished(false);
                }
                
                a.setText(obj.get("text").toString());
                

                commentaires.add(a);


            }
        } catch (IOException ex) {
            
        }
        return commentaires;
    }
    public ArrayList<Commentaire> getAllCommentaires(){
        String url = Statics.BASE_URL+"commentaire/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commentaires = parseCommentaires(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return commentaires;
    }
    public ArrayList<Commentaire> getHotelCommentaires(int id){
        String url = Statics.BASE_URL+"commentaire/mobile/affhotel?id="+id;
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commentaires = parseCommentaires(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return commentaires;
    }


    public boolean addCommentaire(Commentaire u) {
        String url = Statics.BASE_URL + "commentaire/mobile/new?text="+u.getText()+"&iduser="+u.getIduser()+"&idhotel="+u.getIdhotel();//création de l'URL
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

        public boolean editCommentaire(Commentaire u) {
        String url = Statics.BASE_URL + "commentaire/mobile/edit?id="+u.getId()+"&text="+u.getText()+"&ispublish="+u.isIsPublished();//création de l'URL
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

    public boolean deleteCommentaire(int id) {
        String url = Statics.BASE_URL + "commentaire/mobile/del?id=" + id;
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
