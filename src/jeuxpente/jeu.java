/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuxpente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;


/**
 *
 * @author Adrien HOULE & Rohan FERRE
 */
public class jeu {
        public jeu(){
            int pLargeur=19*38; //on fait * 19 comme le plateau fait 19*19 en taille
            int pHauteur=19*38;
            int ilargeur=(int)(pLargeur*0.50);
            
            JFrame lejeu=new JFrame("Jeu de pente");
            
            lejeu.setLayout(new BorderLayout());
            
            lejeu.setSize(pLargeur+ilargeur,pHauteur+20);
            lejeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            InfoJeu i=new InfoJeu(ilargeur,pHauteur);
            i.setPreferredSize(new Dimension(ilargeur,pHauteur));

            
            Plateau p = new Plateau(pLargeur,pHauteur,i);
            p.setPreferredSize(new Dimension(pLargeur,pHauteur));
            
            lejeu.add(p,BorderLayout.CENTER);
            lejeu.add(i,BorderLayout.EAST);
            
            lejeu.setLocationRelativeTo(null);
            lejeu.setResizable(false);
            lejeu.setVisible(true);
        }
}
