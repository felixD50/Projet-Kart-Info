
import java.awt.CardLayout;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class Window extends JFrame {

        PanelMenu menu = new PanelMenu();
        PanelSelectionKart selectionKart = new PanelSelectionKart();
        CardLayout layout = new CardLayout();
        SplitPanelSelection splitSelection = new SplitPanelSelection();   
        final int LARGPIX=1200;
        final int LARGM=500;
        final int HAUTPIX=840;
        final int HAUTM=350;
        double echelle =(double)(LARGPIX)/(double)(LARGM);;
        PanelField field=new PanelField(echelle,HAUTM,1);
        
        
       public Window(){
           setTitle("Pro Kart Racing 2015");
           setSize(LARGPIX,HAUTPIX);
           setLayout(layout);
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setLocationRelativeTo(null);
           
       
           
    add(menu);
    add(field);
    add(selectionKart);
    add(splitSelection);
    field.setVisible(false);
    selectionKart.setVisible(false);
    splitSelection.setVisible(false);
 
    
    //action du button OnePlayer
     menu.OnePlayer.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent actionEvent){
             menu.setVisible(false);
             selectionKart.setVisible(true);
             repaint();
         }
     });
     
     //action du menu Exit        
    menu.Exit.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent actionEvent){
            System.exit(0);
        }
    });
    
     //action du button back to main menu
     selectionKart.Back.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent actionEvent){
             selectionKart.setVisible(false);
             menu.setVisible(true);
             repaint();
         }
     });
    
    //action du button Next
        selectionKart.Next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                repaint();
            }
        });
    
    //action du button Previous
         selectionKart.Previous.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent actionEvent){
                 repaint();
              }
          });
    
    

    //action du button Pick        
    selectionKart.Pick.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent actionEvent){
            selectionKart.setVisible(false);
            field.setVisible(true);
            repaint();
        }
     });
    
    
    //action du button 2 Players
    menu.TwoPlayers.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent actionEvent){
            menu.setVisible(false);
            splitSelection.setVisible(true);
            repaint();
        }
    });



        setVisible(true);
    }
       

        
  
    }

