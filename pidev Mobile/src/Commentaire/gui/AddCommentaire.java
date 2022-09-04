/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package Commentaire.gui;

import Avis.gui.*;
import com.codename1.capture.Capture;
import com.codename1.components.ScaleImageLabel;
import com.codename1.datatransfer.DropTarget;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import com.mycompany.gui.BaseForm;
import com.mycompany.myapp.entities.Avis;
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Hotel;
import com.mycompany.myapp.entities.services.ServiceAvis;
import com.mycompany.myapp.entities.services.ServiceCommentaire;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class AddCommentaire extends BaseForm {

    public AddCommentaire(Resources res, Hotel fi) {
        super("Ajouter Commentaire", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Commentaire");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        
                add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(2, 
                            facebook, twitter
                    )
                )
        ));
                   Label lnom = new Label("Nom Hotel : "+fi.getNom());
                   add(lnom);
                
                        ButtonGroup barGroup = new ButtonGroup();
                  Container co=new Container(BoxLayout.xCenter());;
                    ArrayList <Commentaire> coms = new ArrayList();
                    ServiceCommentaire sa =new ServiceCommentaire();
                    coms=sa.getHotelCommentaires(fi.getId());


                 for (Commentaire fii : coms) {
                            Container ct = new Container(BoxLayout.y());

                            Label l = new Label("ID : "+fii.getId());
                            ct.add(l);
                            Label l2 = new Label("Commentaire : "+fii.getText(),"RedLabel");
                            ct.add(l2);
                            Button Modifier = new Button("Modifier");
                            Button Supprimer = new Button("Supprimer");
                            Modifier.addActionListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {               
                                 new ModifierCommentaire(res,fii,fi).show();                   
                                                    }   
                                            });
                            Supprimer.addActionListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {               
                if (Dialog.show("Confirmation", "Voulez vous supprimer ce commentaire ?", "Oui", "Annuler")) {

                  if( ServiceCommentaire.getInstance().deleteCommentaire(fii.getId()))
                            {
                                Dialog.show("Success","supprimer",new Command("OK"));
                                new AddCommentaire(res,fi).show();
                            }

                            }
                   
                }   
        });
                            
                       ct.add(Modifier);
                       ct.add(Supprimer);


                       Label separator = new Label("","Separator");
                       ct.add(separator);
                       add(ct);
                 }
                 
        TextComponent comment= new TextComponent().label("Commentaire");
        add(comment);

        Button Edit = new Button("Commentaire");
        Edit.addActionListener((evt) -> {
                if (comment.getText().equals(""))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
            ServiceCommentaire sp = new ServiceCommentaire();
                    Commentaire com = new Commentaire();
            com.setIdhotel(fi.getId());
            com.setIduser(2);
            com.setText(comment.getText());
            sp.addCommentaire(com);
            Dialog.show("Success","Commentaire ajouter avec success",new Command("OK"));
            new AddCommentaire(res,fi).show();
                }
        });
        addStringValue("", FlowLayout.encloseRightMiddle(Edit));
        
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
     private Slider createStarRankSlider() {
    Slider starRank = new Slider();
    starRank.setEditable(true);
    starRank.setMinValue(0);
    starRank.setMaxValue(5);
    Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
            derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
    Style s = new Style(0xffff33, 0, fnt, (byte)0);
    Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    s.setOpacity(100);
    s.setFgColor(0);
    Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
    initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
    starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
    return starRank;
}
     
     private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}
}


