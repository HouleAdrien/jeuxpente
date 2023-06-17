/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuxpente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author PC-ADRIEN
 */
public class InfoJeu extends JPanel{
    JLabel nomJoueur1,nomJoueur2;
    JTextField captureJ1,captureJ2;
    JTextField tour;
    
    JButton rejouer;
    
    Color fond;
    int largeur;
    int hauteur;
            
    Font myFont=new Font("Arial",Font.PLAIN,24);
    Color c2=new Color(5,11,91);
    
    public InfoJeu(int l,int h){
        fond = new Color(102,102,255);
        largeur=l;
        hauteur=h;
        this.setSize(largeur,hauteur);
        this.setBackground(fond);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setVisible(true);
        AjoutInfo();
    }
    
    public void AjoutInfo(){
       JPanel j1panel=new JPanel();
       
       j1panel.setLayout(new BoxLayout(j1panel,BoxLayout.Y_AXIS));
       
       j1panel.setSize(largeur,(int)(hauteur*0.45));
       j1panel.setBackground(new Color(137,156,249));
       
       nomJoueur1=new JLabel("Nom joueur 1");
       nomJoueur1.setAlignmentX(Component.CENTER_ALIGNMENT);//centre la case
       nomJoueur1.setFont(myFont);
       nomJoueur1.setForeground(c2);
       nomJoueur1.setHorizontalAlignment(SwingConstants.CENTER);//centre le texte
       
       captureJ1=new JTextField("Capture Joueur 1");
       captureJ1.setAlignmentX(Component.CENTER_ALIGNMENT);//centre la case
       captureJ1.setFont(myFont);
       captureJ1.setForeground(Color.WHITE);
       captureJ1.setBackground(c2);
       captureJ1.setHorizontalAlignment(SwingConstants.CENTER);//centre le texte
        captureJ1.setEditable(false);
      
       j1panel.add(nomJoueur1);
      
       j1panel.add(captureJ1);
      
       
       Border b=BorderFactory.createLineBorder(Color.BLUE,4,true);
       
       j1panel.setBorder(b);
       
      
       this.add(j1panel);
       this.add(Box.createRigidArea(new Dimension(-40, 40)));
        rejouer=new JButton("Nouvelle partie");
        rejouer.setFont(myFont);
        this.add(rejouer);
        this.add(Box.createRigidArea(new Dimension(-40, 40)));
       JPanel j2panel=new JPanel();
       
       j2panel.setLayout(new BoxLayout(j2panel ,BoxLayout.Y_AXIS));
       
       j2panel.setSize(largeur,(int)(hauteur*0.45));
       j2panel.setOpaque(false);
       
       nomJoueur2=new JLabel("Nom joueur 2");
       nomJoueur2.setAlignmentX(Component.CENTER_ALIGNMENT);//centre la case
       nomJoueur2.setFont(myFont);
       nomJoueur2.setForeground(Color.WHITE);
       nomJoueur2.setHorizontalAlignment(SwingConstants.CENTER);//centre le texte
       
       captureJ2=new JTextField("Capture Joueur 2");
       captureJ2.setAlignmentX(Component.CENTER_ALIGNMENT);//centre la case
       captureJ2.setFont(myFont);
       captureJ2.setForeground(c2);
       captureJ2.setHorizontalAlignment(SwingConstants.CENTER);//centre le texte
       captureJ2.setEditable(false);
      
       j2panel.add(nomJoueur2);
      
       j2panel.add(captureJ2);
      
       
       Border b2=BorderFactory.createLineBorder(Color.BLUE,4,true);
       
       j2panel.setBorder(b2);
       
        
        this.add(j2panel);
        this.add(Box.createRigidArea(new Dimension(-40, 40)));
        //connaître tour
        JPanel tour2=new JPanel();
        tour2.setLayout(new BoxLayout(tour2,BoxLayout.Y_AXIS));
        tour2.setSize(largeur,(int)(hauteur*0.45));
        tour2.setOpaque(false);
        
        tour=new JTextField("personne entrain de jouer");
        tour.setAlignmentX(Component.CENTER_ALIGNMENT);
        tour.setFont(myFont);
        tour.setForeground(c2);
        tour.setHorizontalAlignment(SwingConstants.CENTER);
        tour.setEditable(false);
        tour2.add(tour);
        
        Border b3=BorderFactory.createLineBorder(Color.BLUE,4,true);
        tour2.setBorder(b3);
        
        this.add(tour2);
    }
    
    public void setName(String n,int j){
        if(j==Plateau.pierre1){
            nomJoueur1.setText("joueur 1:"+n);
        }else{
            nomJoueur2.setText("joueur 2:"+n);
        }
         repaint();   
   }
    
    public void setCapture(int c,int joueur){
        if(joueur==Plateau.pierre1){
            captureJ1.setText(Integer.toString(c));
        }else{
            captureJ2.setText(Integer.toString(c));
        }
         repaint();   
   }
    public void setPlayerTurn(int queljoueur){
       if(queljoueur==Plateau.pierre1){
          tour.setBackground(c2);
          tour.setForeground(Color.WHITE);
          String n=nomJoueur1.getText();
          tour.setText("tour de "+n);
        }else{
           tour.setBackground(Color.WHITE);
           tour.setForeground(c2);
           String n=nomJoueur2.getText();
          tour.setText("tour de "+n);
        }
    }
}
