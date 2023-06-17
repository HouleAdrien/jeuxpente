/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuxpente;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

/**
 *
 * @author Adrien HOULE & Rohan FERRE 
 */
public class Plateau extends JPanel implements MouseListener {
    
    public static final int vide = 0;
    public static final int pierre1 = 1;
    public static final int pierre2 = -1;
    public static final int nb_carre = 19;
    public static final int interieur_depart = 7;
    public static final int interieur_fin = 11;
    public static final int TOUR_JOUEUR1 = 1;
    public static final int TOUR_JOUEUR2 = -1; 
    public static int nbTour; 
    public static boolean victoire; 
    
    private int pLargeur, pHauteur;
    
    private int carreL, carreH;
    private JFrame myFrame; 
    
    public int tourJoueur; 
    public static String nomJoueur1, nomJoueur2, gagnant; 
    public int captureJ1, captureJ2; 
    public boolean aligneJ1, aligneJ2;
    
    private CarrePlateau[][] gameboard;
    private InfoJeu info; //ajout
    
    public Plateau(int l, int h,InfoJeu i){//ajout
    pLargeur = l;
    pHauteur = h;
    info=i;//ajout
    this.setSize(l,h);
    this.setBackground(Color.CYAN);
    
    carreL = pLargeur/this.nb_carre;
    carreH = pHauteur/this.nb_carre;
    
    gameboard = new CarrePlateau[nb_carre][nb_carre];
    
       for(int ligne = 0 ; ligne < nb_carre ; ligne++)
       {
           for(int colonne = 0 ; colonne < nb_carre ; colonne++)
           {
               gameboard[ligne][colonne] = new CarrePlateau(colonne * carreL, ligne * carreH, carreL, carreH);
                if(colonne >= interieur_depart && colonne <= interieur_fin)
                {
                    if(ligne >= interieur_depart && ligne <= interieur_fin)
                    {
                        gameboard[ligne][colonne].setInner();  
                    }
                }
           }
       }
       startNewGame(); 
       addMouseListener(this); 
    }
    
    //methode de dessin
    public void paintComponent(Graphics g)
    {
       g.fillRect(0, 0, pLargeur, pHauteur);
       for(int ligne = 0 ; ligne < nb_carre ; ligne++)
           for(int colonne = 0 ; colonne < nb_carre ; colonne++)
                gameboard[ligne][colonne].drawMe(g);
    }
    
    public void resetBoard()
    {
        for (int ligne = 0 ; ligne < nb_carre ; ligne++)
            for (int colonne = 0 ; colonne < nb_carre ; colonne++)
                gameboard[ligne][colonne].setState(vide);
    }
    
    public void startNewGame() 
    {
        resetBoard(); 
        
        captureJ1 = 0;
        captureJ2 = 0; 
        aligneJ1 = false;
        aligneJ2 = false;
        victoire = false;
        
        nomJoueur1 = JOptionPane.showInputDialog("Nom du joueur 1"); 
        while (nomJoueur1.equals(""))
            nomJoueur1 = JOptionPane.showInputDialog("Veuillez rentrer un nom pour le joueur 1");
        
        info.setName(nomJoueur1,pierre1);//ajout
        info.setCapture(captureJ1,pierre1);//ajout
        
        nomJoueur2 = JOptionPane.showInputDialog("Nom du joueur 2"); 
        while (nomJoueur2.equals(""))
            nomJoueur2 = JOptionPane.showInputDialog("Veuillez rentrer un nom pour le joueur 2");
        
        info.setName(nomJoueur2,pierre2);//ajout
        info.setCapture(captureJ2,pierre2);//ajout
        nbTour = 1; 
        
        tourJoueur = this.TOUR_JOUEUR1;  
    }
    
    public void changePlayerTurn() 
    {
        nbTour++; 
        tourJoueur *= -1; 
        info.setPlayerTurn(tourJoueur);//ajout
    }
    
    public boolean winPlayer(int tourJoueur)
    {
        boolean win = false; 
        if(tourJoueur == this.TOUR_JOUEUR1)
            if(captureJ1 > 5 || aligneJ1 == true)
            {
                win = true; 
                gagnant = nomJoueur1; 
            }
        if(tourJoueur == this.TOUR_JOUEUR2)
            if(captureJ2 > 5 || aligneJ2 == true)
            {
                win = true; 
                gagnant = nomJoueur2; 
            }
        return win; 
    }
    
    // Cette fonction check sur quel carré du plateau on a cliqué 
    public void checkClick(int clickX, int clickY)
    {
        for(int ligne = 0 ; ligne <  nb_carre ; ligne++)
            for (int colonne = 0 ; colonne < nb_carre ; colonne++)
            {
                boolean cliqueCarré = false;
                if (nbTour == 1) // Si c'est le premier coup du premier joueur, il est obligé de placer son premier piont dans le carré de départ
                {
                    if((ligne >= interieur_depart && ligne <= interieur_fin) && (colonne >= interieur_depart && colonne <= interieur_fin)) // On vérifie si le click est bien dans le carré de départ 
                        cliqueCarré = gameboard[ligne][colonne].isClicked(clickX, clickY); // et si c'est le cas, on place le pion
                    if(cliqueCarré) 
                    {
                        System.out.println("Vous avez cliqué sur le carré à [" + ligne + ", " + colonne + "]");
                        gameboard[ligne][colonne].setState(tourJoueur);
                        this.repaint();
                        this.changePlayerTurn();
                    }
                }
                else
                {
                    cliqueCarré = gameboard[ligne][colonne].isClicked(clickX, clickY);
                    if(cliqueCarré) 
                    {
                        System.out.println("Vous avez cliqué sur le carré à [" + ligne + ", " + colonne + "]");
                        gameboard[ligne][colonne].setState(tourJoueur);
                        checkForCaptures(ligne, colonne, tourJoueur); 
                        checkForAlignement(ligne, colonne, tourJoueur);
                        this.repaint();
                        victoire = this.winPlayer(tourJoueur); 
                        if (!victoire)
                        {
                            // debog
                            //System.out.println("Tour : " + nbTour + " TourJoueur : " + tourJoueur + " J1capture : " + captureJ1 + " J2Capture : " + captureJ2);
                            this.changePlayerTurn();
                        }
                        else
                        {
                            this.finDeJeu(); 
                            FenetreVictoire vic = new FenetreVictoire(this.gagnant, this); 
                        }
                    }
                }
            }
    }
    
    void finDeJeu()
    {
        for(int ligne = 0 ; ligne <  nb_carre ; ligne++)
            for (int colonne = 0 ; colonne < nb_carre ; colonne++)
                gameboard[ligne][colonne].setUtilisé(true); 
    }
    
    public void checkForCaptures(int l, int c, int t)
    {
        boolean capturé; 
        // Check pour les captures Horizontal 
        if(c <= nb_carre)
            capturé = checkCapturesCroixDirectionelle(l, c, t, 0, 1);
        if(c >= 3)
            capturé = checkCapturesCroixDirectionelle(l, c, t, 0, -1); 
        
        // Check pour les captures Verticales
        if(l <= nb_carre)
            capturé = checkCapturesCroixDirectionelle(l, c, t, 1, 0);
        if(c >= 3)
            capturé = checkCapturesCroixDirectionelle(l, c, t, -1, 0);     
        
        // Check pour les captures diagonales 
        if(l <= nb_carre)
            capturé = checkCapturesDiagonales(l, c, t, 1, 1);
        if(c >= 3)
            capturé = checkCapturesDiagonales(l, c, t, -1, -1);  
    }
    
    public boolean checkCapturesCroixDirectionelle(int l, int c, int t, int hautBas, int droiteGauche)
    {
        boolean capture = false; 
        
        if(gameboard[l + hautBas][c + droiteGauche].getState() == t * -1)
        {
            if(gameboard[l + (hautBas * 2)][c + (droiteGauche * 2)].getState() == t * -1)
            {
                if(gameboard[l + (hautBas * 3)][c + (droiteGauche * 3)].getState() == t)
                {
                    System.out.println("Capture directionnelle" + droiteGauche);
                    //On les enlèves 
                    gameboard[l + hautBas][c + droiteGauche].setState(vide);
                    gameboard[l + (hautBas * 2)][c + (droiteGauche * 2)].setState(vide); 
                    capture = true; 
                    if(t == this.TOUR_JOUEUR1)
                    {
                        captureJ1 += 2; 
                        //L'ajouter sur le tableau des scores
                    }
                    else 
                    {
                        captureJ2 += 2; 
                        //L'ajouter sur le tableau des scores
                    }    
                }
            }
        }
        return capture; 
    }
    
    public boolean checkCapturesDiagonales(int l, int c, int t, int hautBas, int droiteGauche)
    {
        boolean capture = false; 
        
        if(gameboard[l - hautBas][c + droiteGauche].getState() == t * -1)
        {
            if(gameboard[l - (hautBas * 2)][c + (droiteGauche * 2)].getState() == t * -1)
            {
                if(gameboard[l - (hautBas * 3)][c + (droiteGauche * 3)].getState() == t)
                {
                    System.out.println("Capture diagonale" + droiteGauche);
                    //On les enlèves 
                    gameboard[l - hautBas][c + droiteGauche].setState(vide);
                    gameboard[l - (hautBas * 2)][c + (droiteGauche * 2)].setState(vide); 
                    capture = true; 
                    if(t == this.TOUR_JOUEUR1)
                    {
                        captureJ1 += 2; 
                        //L'ajouter sur le tableau des scores
                    }
                    else 
                    {
                        captureJ2 += 2; 
                        //L'ajouter sur le tableau des scores
                    }    
                }
            }
        }
        return capture; 
    }
    
    public void checkForAlignement(int l, int c, int t)
    {
        boolean aligné; 
        // Check pour les alignement Horizontal 
        if(c <= nb_carre)
            aligné = checkAlignementCroixDirectionelle(l, c, t, 0, 1);
        if(c >= 3)
            aligné = checkAlignementCroixDirectionelle(l, c, t, 0, -1); 
        
        // Check pour les alignement Verticales
        if(l <= nb_carre)
            aligné = checkAlignementCroixDirectionelle(l, c, t, 1, 0);
        if(c >= 3)
            aligné = checkAlignementCroixDirectionelle(l, c, t, -1, 0);     
        
        // Check pour les alignement diagonales 
        if(l <= nb_carre)
            aligné = checkAlignementDiagonales(l, c, t, 1, 1);
        if(c >= 3)
            aligné = checkAlignementDiagonales(l, c, t, -1, -1);  
    }
    
    public boolean checkAlignementCroixDirectionelle(int l, int c, int t, int hautBas, int droiteGauche)
    {
        boolean alignement = false; 
        int alignes = 1; 
        
        if(gameboard[l + hautBas][c + droiteGauche].getState() == t)
        {
            alignes++; 
            if(gameboard[l + (hautBas * 2)][c + (droiteGauche * 2)].getState() == t)
            {
                alignes++; 
                if(gameboard[l + (hautBas * 3)][c + (droiteGauche * 3)].getState() == t)
                {
                    alignes++; 
                    if(gameboard[l + (hautBas * 4)][c + (droiteGauche * 4)].getState() == t)
                    {
                        alignes++;    
                    }
                }
            }
        }
        if(t == this.TOUR_JOUEUR1 && alignes >= 5)
        {
            aligneJ1 = true; 
            //L'ajouter sur le tableau des scores
        }
        if (t == this.TOUR_JOUEUR2 && alignes >= 5)
        {
            aligneJ2 = true;  
            //L'ajouter sur le tableau des scores
        } 
        return alignement; 
    }
    
    public boolean checkAlignementDiagonales(int l, int c, int t, int hautBas, int droiteGauche)
    {
        boolean alignement = false; 
        int alignes; 
        
        /*if(t == this.TOUR_JOUEUR1)
        {
            
        }
        else
        {
            
        }*/
        
        if(gameboard[l - hautBas][c + droiteGauche].getState() == t * -1)
        {
            if(gameboard[l - (hautBas * 2)][c + (droiteGauche * 2)].getState() == t * -1)
            {
                if(gameboard[l - (hautBas * 3)][c + (droiteGauche * 3)].getState() == t)
                {
                    System.out.println("Capture diagonale" + droiteGauche);
                    //On les enlèves 
                    gameboard[l - hautBas][c + droiteGauche].setState(vide);
                    gameboard[l - (hautBas * 2)][c + (droiteGauche * 2)].setState(vide); 
                    alignement = true; 
                    if(t == this.TOUR_JOUEUR1)
                    {
                        aligneJ1 = true; 
                        //L'ajouter sur le tableau des scores
                    }
                    else 
                    {
                        aligneJ2 = true; 
                        //L'ajouter sur le tableau des scores
                    }    
                }
            }
        }
        return alignement; 
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        this.checkClick(e.getX(), e.getY());
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {}
    
    @Override
    public void mouseReleased(MouseEvent e)
    {}
    
    @Override 
    public void mouseEntered(MouseEvent e)
    {}
    
    @Override
    public void mouseExited(MouseEvent e)
    {}
}
