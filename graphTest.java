import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.LinkedList;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.Timer;

public class graphTest extends JFrame {
    Image background;
    BufferedImage ArrierePlan;
    Graphics buffer;
    int xpos=0;
    int toucheHaut=69;
    int toucheBas=70;
    int toucheGauche=81;
    int toucheDroite=68;
    Kart kart1;
    boolean ToucheHaut,ToucheBas,ToucheDroite,ToucheGauche;
    char tourne;
    char freine;
    LinkedList <Item> Items;//liste de tous les objets actifs
    

    
    
    
    class TimerAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            boucle_principale_jeu();
        }
    }

    public graphTest(){
        try{
            background=ImageIO.read(new File("stars.jpg"));
        }catch (IOException e){
            System.out.println("Could not load image file");
            System.exit(1);
        }
        ToucheHaut=false;ToucheBas=false;ToucheDroite=false;ToucheGauche=false; 
        
        this.setTitle("Karting");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,576);
        this.setVisible(true);
        Rectangle Ecran=new Rectangle(getInsets().left,getInsets().top,getSize().width-getInsets().right-getInsets().left,getSize().height-getInsets().bottom-getInsets().top);
        kart1=new Kart(300,300,0,1,15,10,0.7,150,1,1);
        this.addKeyListener(new graphTest_this_keyAdapter(this));
        ArrierePlan=new BufferedImage(2000,2000,BufferedImage.TYPE_INT_RGB);
        buffer=ArrierePlan.getGraphics();
        Items=new LinkedList <Item>();
        Items.add(kart1);
        Timer timer=new Timer(25,new TimerAction());
        timer.start();
        
        
    }
    public void boucle_principale_jeu(){
        if (ToucheBas){
            freine='y';
            kart1.freine();
        }
        if (ToucheGauche){
            tourne='g';
        }
        else if (ToucheDroite){
            tourne='d';
        }
        else{
            tourne='0';
        }
        if (ToucheHaut && (ToucheGauche || ToucheDroite )) { 
            kart1.avance(1); 
        }
        else if (ToucheHaut){
            kart1.avance(0);
        }
        kart1.tourne(tourne);
        if (ToucheHaut==false && (ToucheGauche || ToucheDroite )){ 
            kart1.ralentit(1); 
        }
        else if (ToucheHaut==false){
            kart1.ralentit(0);
        }
        kart1.derapage(tourne,freine);
        freine='n';
        repaint();
        
    }
    public void paint(Graphics g){
        //int xc=getSize().width/2;
       // int yc=getSize().height/2;
        //buffer.setColor(Color.black);
        //buffer.fillRect(0,0,2000,2000);
        buffer.drawImage(background,0,0,this);
        //Graphics2D g2d=(Graphics2D)buffer;//partie où on fait pivoter l'image
        //AffineTransform transformer=g2d.getTransform();
        kart1.drawGraphTest(buffer);
        //System.out.println(thetaIm);
        //g2d.rotate(-thetaIm,kart1.getX(),kart1.getY());
        g.drawImage(ArrierePlan,0,0,this);
    }
    
     void this_keyPressed(KeyEvent e){
        int code= e.getKeyCode();
        System.out.println("Key pressed : "+code);
        if (code==68){
             ToucheDroite=true;
        }
        
        else if (code==81){
             ToucheGauche=true;
        } 
        
        else if (code==83){
            ToucheBas=true;
        } 
        
        else if (code==90){
            ToucheHaut=true;
        }
        
        if (ToucheHaut && ToucheBas){
            ToucheHaut=false;
            ToucheBas=false;
        }
        if (ToucheGauche && ToucheDroite){
            ToucheGauche=false;
            ToucheDroite=false;
        }
    }

    void this_keyReleased(KeyEvent e){
        int code=e.getKeyCode();
        System.out.println("Key released : "+code);
        if (code==68){
            ToucheDroite=false;
        }
        else if (code==81){
                ToucheGauche=false;
        }   
        else if (code==83){
            ToucheBas=false;
        } 
        else if (code==90){
                ToucheHaut=false;
        }
    } 
    
    private class graphTest_this_keyAdapter extends KeyAdapter{
        private graphTest adaptee;
        graphTest_this_keyAdapter(graphTest adaptee){
           this.adaptee=adaptee; 
        }
        public void keyPressed(KeyEvent e){
            adaptee.this_keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            adaptee.this_keyReleased(e);
        }  
        
    }
    public static void main(String args[]){
        graphTest fenetre=new graphTest();
    }

}
