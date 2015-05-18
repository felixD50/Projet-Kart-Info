import java.awt.Component;

import java.awt.Dimension;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.Image;

import java.io.File;

import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PanelMenu extends JPanel {
    
    JButton OnePlayer = new JButton("1 Player");
    JButton TwoPlayers = new JButton("2 Players");
    JButton Exit = new JButton ("Exit");
    
    Image backgroundMenu; 
    
    public PanelMenu(){
        
        //BoxLayout => placement vertical
        setLayout(new BoxLayout (this, BoxLayout.PAGE_AXIS)); 
        
        OnePlayer.setMaximumSize(new Dimension (350,100)); //r�gle la taille horizontale
        OnePlayer.setPreferredSize(new Dimension (350,100));//r�glle la taille verticale 
        
        TwoPlayers.setMaximumSize(new Dimension (350,100));
        TwoPlayers.setPreferredSize(new Dimension (350,100));
        
        Exit.setMaximumSize(new Dimension (350,100));
        Exit.setPreferredSize(new Dimension (350,100));
        
        //R�gle la taille du texte dans chaque button
        Font newButtonFont=new Font(OnePlayer.getFont().getName(),OnePlayer.getFont().getStyle(),40);
        OnePlayer.setFont(newButtonFont);
        TwoPlayers.setFont(newButtonFont);
        Exit.setFont(newButtonFont);
            
            
        OnePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        TwoPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        Exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Placer des espaces entre les button
        add(Box.createVerticalGlue());
        add(OnePlayer);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(TwoPlayers);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(Exit);
        add(Box.createVerticalGlue());
        
        try { 
            System.out.println("Peach Mario Kart.png");
            backgroundMenu= ImageIO.read(new File("Peach Mario Kart.png")); 
            System.out.println("Peach Mario Kart"+backgroundMenu);
        }catch(Exception err) { 
            System.out.println("Peach Mario Kart.png"+" introuvable !");             
            System.exit(1); 
            } 
    }
    
    public void paint(Graphics g){
       g.drawImage(backgroundMenu,0,0,getWidth(),getHeight(),null); //paint l'image en adapatant la taille de l'�cran
       OnePlayer.repaint();
       TwoPlayers.repaint();
       Exit.repaint();
    }
}
