import java.awt.Color;
import java.awt.Graphics;

public class Bombe extends Projectile{//WORK IN PROGRESS il faut trouver comment détecter s'il y a des karts dans la zone d'explosion quand elle explose
    
    private static double rayExpl;//le rayon de l'explosion
    
    public Bombe(double x, double y,double dx, double dy){
        super(x,y,dx,dy);  
        rayExpl=3;
        this.nomObjet="BOMBE";
    }
    
    public boolean quandExploser(){
        boolean exp=false;
        if (tempsVie>160){//la bombe explose au bout de 4 secondes, on incrémente le temps de vie toutes les 25ms --->160 itérations en tout
            exp=true;
        }return exp;
    }
    public void doCollision(Item item){
        
    }
    
    public void move(){
        
    }
   
    public void drawGraphTest(Graphics g){
        g.setColor(Color.black);
        g.fillOval((int)x,(int)(576-y),10,10);//rayon du cercle à adapter aver l'échelle
    }
}
