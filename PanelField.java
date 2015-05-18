

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelField extends JPanel {
    int x=0;
    int y=0;
    static double aExt=200;
    static double aInt=188;
    static double bExt=125;
    static double bInt = 113;
    double c=0;
    int hWind;
    
    double echelle;
    
    int[]tabXext = new int[801];
    int[]tabYext = new int[801];
    int[]tabXint = new int[753];
    int[]tabYint = new int[753];
 
    BufferedImage ArrierePlan;
    Graphics buffer; 
    
    int toucheHaut=69;
    int toucheBas=70;
    int toucheGauche=81;
    int toucheDroite=68;
    boolean ToucheHaut,ToucheBas,ToucheDroite,ToucheGauche;
    
    char tourne;
    char freine;
    double thetaIm;
    int xpos=0;
    Kart kart1;
    
    int numJoueur;
    
    
       
    public PanelField(double ech, int h, int nJ){
        
        numJoueur=nJ;
        
        ArrierePlan=new BufferedImage(2000,2000,BufferedImage.TYPE_INT_RGB);
        buffer=ArrierePlan.getGraphics();
        
        ToucheHaut=false;ToucheBas=false;ToucheDroite=false;ToucheGauche=false;
        this.addKeyListener(new PanelField_this_keyAdapter(this));
        
        
        kart1=new Kart(250,56,0,1,15,10,0.7,150,1,1);
        
        hWind =h;
        echelle=ech;
        
        
        //demi ellipse extérieure positive
        for (int i =0; i<400; i++){
            tabXext[i]=i-200;
            tabYext[i]= (int) Math.sqrt( bExt*bExt*(1.0-tabXext[i]*tabXext[i]/aExt/aExt));
        }
    
        //demi ellipse extérieure négative
    
        for (int i=400;i<801;i++){
            tabXext[i]=(int) (i-c-200);
            tabYext[i] = (int) -Math.sqrt(bExt*bExt*(1.0-(tabXext[i]*tabXext[i]/aExt/aExt)));
            c=c+2;
        }

        //demi ellipse intérieure positive
        for (int i =0; i<376; i++){
            tabXint[i]=i-188;
            tabYint[i]= (int) Math.sqrt(bInt*bInt*(1.0-(tabXint[i]*tabXint[i]/aInt/aInt)));
        }    
    
        //demi ellipse intérieure négative
        c=0; 
    
        for (int i=376;i<753;i++){
            tabXint[i]=(int) (i-c-188);
            tabYint[i]= (int) -Math.sqrt(bInt*bInt*(1.0-(tabXint[i]*tabXint[i]/aInt/aInt)));
            c=c+2;
        }  
        
        Timer timer=new Timer(25,new TimerAction());
        timer.start();
     
    }
    public void paint(Graphics g){
       int x1=0;
       int x2=0;
       int y1=0;
       int y2=0;
       
        buffer.setColor( new Color(0, 150, 0) );
        buffer.fillRect(0,0,1200,840);
        buffer.setColor( Color.black );
               
       //Ellispe Extérieure
       for(int i=0; i<800; i++){
           
           x1=this.m2SX(tabXext[i]+250, tabYext[i]+175,0, hWind, hWind, 0, echelle);
           x2=this.m2SX(tabXext[i+1]+250, tabYext[i+1]+175,0, hWind, hWind, 0, echelle);
           y1=this.m2SY(tabXext[i]+250, tabYext[i]+175,0, hWind, hWind, 0, echelle);
           y2=this.m2SY(tabXext[i+1]+250, tabYext[i+1]+175,0, hWind, hWind, 0, echelle);
           buffer.drawLine(x1,y1,x2,y2);
          // System.out.println(tabYext[i]);
            
       }
       
       
       // Ellispe Intérieure
       for(int i=0; i<752; i++){
           x1=this.m2SX(tabXint[i]+250, tabYint[i]+175,0, hWind, hWind, 0, echelle);
           x2=this.m2SX(tabXint[i+1]+250, tabYint[i+1]+175,0, hWind, hWind, 0, echelle);
           y1=this.m2SY(tabXint[i]+250, tabYint[i]+175,0, hWind, hWind, 0, echelle);
           y2=this.m2SY(tabXint[i+1]+250, tabYint[i+1]+175,0, hWind, hWind, 0, echelle);
           
           buffer.drawLine(x1,y1,x2,y2);
       }
       
        
        int X=this.m2SX((int)(kart1.getX()),(int) (kart1.getY()),0, hWind, hWind, 0, echelle);
        int Y=this.m2SY((int)(kart1.getX()),(int) (kart1.getY()),0, hWind, hWind, 0, echelle);
        kart1.draw(buffer,X,Y);
        
        g.drawImage(ArrierePlan,0,0,this);
    }
    
    
    
    class TimerAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            thetaIm=0;
            boucle_principale_jeu();
        }
        
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

    /** Convertit la coordonnée X métrique en pixel d'une fenêtre donnée
     * @param mx la coord x en metrique
     * @param my la coord y en metrique
     * @param fx la coord x en metrique de la fenêtre en haut à gauche
     * @param fy la coord y en metrique de la fenêtre en haut à gauche
     * @param fh la hauteur en metrique de la fenêtre
     * @param a l'angle de la fenêtre en radian// pour l'instant, a positif comprit entre 0 et Pi
     * @param ech le rapport pixel/metrique
     * @return la coordonée X**/
     public int m2SX(int mx,int my, int fx, int fy, int fh,double a,double ech){
         double DX,DY,xA,yA,X;
         if(a>=0){
            xA=fx+fh*Math.sin(a);
            yA=fy-fh*Math.cos(a);
            DX=Math.abs(mx-xA);            
            DY=Math.abs(my-yA);
            X=Math.cos(a)*DX+Math.sin(a)*DY;       
         }else {
            xA=fx-fh*Math.sin(-a);
            yA=fy-fh*Math.cos(-a);
            DX=Math.abs(mx-xA);            
            DY=Math.abs(my-yA);
            X=Math.cos(a)*DY-Math.sin(a)*DX;  
         }         
         return (int)(X*ech); 
     }
    
     /** Convertit la coordonnée Y métrique en pixel d'une fenêtre donnée
      * @param mx la coord x en metrique
      * @param my la coord y en metrique
      * @param fx la coord x en metrique de la fenêtre en haut à gauche
      * @param fy la coord y en metrique de la fenêtre en haut à gauche
      * @param fh la hauteur en metrique de la fenêtre
      * @param a l'angle de la fenêtre en radian
      * @param ech le rapport pixel/metrique
      * @return la coordonée Y**/
    public int m2SY(int mx,int my, int fx, int fy, int fh,double a,double ech){
        double DX,DY,xA,yA,Y;
        if(a>=0){
            xA=fx+fh*Math.sin(a);
            yA=fy-fh*Math.cos(a);
            DX=Math.abs(mx-xA);            
            DY=Math.abs(my-yA);
            Y=Math.cos(a)*DY-Math.sin(a)*DX;
        }else {
            xA=fx-fh*Math.sin(-a);
            yA=fy-fh*Math.cos(-a);
            DX=Math.abs(mx-xA);            
            DY=Math.abs(my-yA);
            Y=Math.cos(a)*DX+Math.sin(a)*DY;  
        }
        return (int)(840-Y*ech); 
    }
    
    private class PanelField_this_keyAdapter extends KeyAdapter{
        private PanelField adaptee;
        PanelField_this_keyAdapter(PanelField adaptee){
           this.adaptee=adaptee; 
           
        }
        public void keyPressed(KeyEvent e){
            System.out.println(e);
            adaptee.this_keyPressed(e);
            
        }
        public void keyReleased(KeyEvent e){
            adaptee.this_keyReleased(e);
        }

    }   
    
    void this_keyPressed(KeyEvent e){
        int code= e.getKeyCode();
        System.out.println("Key pressed : "+code);
        
        if(numJoueur==1){               // Joueur 1 : pavé QZDS
            if (code==68){
                ToucheDroite=true;
            }else if (code==81){
                ToucheGauche=true;
            }else if (code==83){
                ToucheBas=true;
            }else if (code==90){
                ToucheHaut=true;
            }
        }else if (numJoueur==2){       // Joueur 2 : pavé flèches
            if (code==39){
                ToucheDroite=true;
            }else if (code==37){
                ToucheGauche=true;
            }else if (code==40){
                ToucheBas=true;
            }else if (code==38){
                ToucheHaut=true;
            }
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
        
        if(numJoueur==1){               // Joueur 1 : pavé QZDS
            if (code==68){
                ToucheDroite=false;
            }else if (code==81){
                ToucheGauche=false;
            }else if (code==83){
                ToucheBas=false;
            }else if (code==90){
                ToucheHaut=false;
            }
        }else if (numJoueur==2){       // Joueur 2 : pavé flèches
            if (code==39){
                ToucheDroite=false;
            }else if (code==37){
                ToucheGauche=false;
            }else if (code==40){
                ToucheBas=false;
            }else if (code==38){
                ToucheHaut=false;
            }
        }
        
        
    } 
    
    
    
}
    
    




