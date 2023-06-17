/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuxpente;
import java.awt.Color;
import java.awt.Graphics;
import jeuxpente.Plateau;


/**
 *
 * @author Adrien HOULE & Rohan FERRE
 */
public class CarrePlateau {
    private int x, y;
    private int cLargeur, cHauteur;
    private int cEtat;//ouvert , joueur 1 , joueur 2
    private Color cCouleur; //couleur du carré
    private Color lCouleur; //Couleur de la croix du carré
    private Color bCouleur; //couleur bordure du carré
    private Color interieurC;//couleur pour le carré 5x5 de départ
    private Color pierre1Couleur = new Color(35, 16, 120); 
    private Color pierre2Couleur = new Color(247, 245, 186);
    boolean isInner = false;
    boolean estUtilisé = false; 
    
    //constructeur
    
    public CarrePlateau(int x_, int y_, int l, int h){
        x = x_;
        y = y_;
        cLargeur = l;
        cHauteur = h;
        
        cCouleur = new Color(249,218,124);
        lCouleur = new Color(83,85,89);
        bCouleur = new Color(244,212,137);
        interieurC = new Color(255,238,144);
        
        cEtat = Plateau.vide;  
    }
    public void setInner(){
        isInner = true;
    }
    
    public void drawMe(Graphics g)
    {
        //couleur du carré
        if(isInner)
        {
            g.setColor(interieurC);
        }
        else
        {
            g.setColor(cCouleur);
        }
        g.fillRect(x,y,cLargeur,cHauteur);

        //bordure
        g.setColor(bCouleur);
        g.drawRect(x,y,cLargeur,cHauteur);

        g.setColor(lCouleur);
        //création d'une croix
        //ligne horizontale
        g.drawLine(x,y+cHauteur/2,x+cLargeur,y+cHauteur/2);
        //ligne verticale
        g.drawLine(x+cLargeur/2,y,x+cLargeur/2,y+cHauteur);

        if (cEtat == Plateau.pierre1)
        {
            g.setColor(pierre1Couleur);
            g.fillOval(x + 3, y + 3, cLargeur - 6, cHauteur - 6);
        }
        
        if (cEtat == Plateau.pierre2)
        {
            g.setColor(pierre2Couleur);
            g.fillOval(x + 3, y + 3, cLargeur - 6, cHauteur - 6);
        }
    }
    
    /* Il y a seulement 3 états possibles : 
    vide = 0
    pierre1 = 1
    pierre2 = -1
    */
    public void setState(int newState)
    {
        if (newState < -1 || newState > 1)
            System.out.println(newState + "n'est pas un état connu. L'état du plateau peut seulement être compris entre -1 et 1");
        else
            cEtat = newState;
        if (cEtat == 0)
            estUtilisé = false; 
    }
    
    public int getState()
    {
        return cEtat; 
    }
    
    public void setUtilisé(boolean utilisé)
    {
        estUtilisé = utilisé; 
    }
    
    // C'est utilisé avec la méthode checkClick in Plateau            
    public boolean isClicked(int clickX, int clickY)
    {
        boolean clique = false; 
        if (!estUtilisé)
            if(x < clickX && clickX < x + cLargeur)
                if (y < clickY && clickY < y + cHauteur)
                {
                    clique = true; 
                    estUtilisé = true;
                }
            return clique; 
    }
}
