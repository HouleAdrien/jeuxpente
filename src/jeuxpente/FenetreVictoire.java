/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuxpente;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 

/**
 *
 * @author ferre
 */
public class FenetreVictoire extends JFrame implements ActionListener{
    
    public FenetreVictoire(String gagnant, Plateau p)
    { 
        JFrame vic = new JFrame("Victoire"); 
        vic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vic.setSize(400, 400);
        vic.setLocationRelativeTo(null);
        vic.setResizable(false);
        vic.setAlwaysOnTop(true);        
        
        JPanel panel = new JPanel(); 
        GridLayout layout = new GridLayout(3, 1); 
        panel.setLayout(layout); 
        
        JButton rejouer = new JButton("Rejouer");
        rejouer.setActionCommand("rejouer");
         rejouer.addActionListener(e -> {
         vic.dispose();
         p.startNewGame();
      });
        rejouer.setBounds(100, 150, 50, 50);
        panel.add(rejouer);
        
        JButton quitter = new JButton("Quitter"); 
        quitter.setActionCommand("quitter");
         quitter.addActionListener(e -> {
             System.exit(0);
      });
        
        quitter.setBounds(100, 250, 50, 50);
         panel.add(quitter);
        JEditorPane editeur = new JEditorPane(); 
        editeur.setContentType("text/html");
        editeur.setText("<p>" + gagnant + " a gagné la partie ! <br> Pour relancer une partie, cliquer sur le bouton <b>Rejouer</b>. <br> Pour quitter le jeu, cliquer sur le bouton <b>Quitter</b><br></p>");
        panel.add(editeur);       
        vic.add(panel);
        vic.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    


    
                                   
