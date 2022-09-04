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
import com.mycompany.myapp.entities.Hotel;

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
public class ServiceHotel {

    public ArrayList<Hotel> hotels;
    
    public static ServiceHotel instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceHotel() {
         req = new ConnectionRequest();
    }

    public static ServiceHotel getInstance() {
        if (instance == null) {
            instance = new ServiceHotel();
        }
        return instance;
    }
    


    public ArrayList<Hotel> parseHotels(String jsonText){
                try {

                    System.out.println(jsonText);
            hotels=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Hotel a = new Hotel();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);

                a.setNom(obj.get("nom").toString());
                        

                hotels.add(a);


            }
        } catch (IOException ex) {
            
        }
        return hotels;
    }
    public ArrayList<Hotel> getAllHotels(){
        String url = Statics.BASE_URL+"hotel/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                hotels = parseHotels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return hotels;
    }



}
