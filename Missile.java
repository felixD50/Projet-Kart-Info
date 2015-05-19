import java.awt.Color;
import java.awt.Graphics;

public class Missile extends Projectile {
    
    public Missile(double x, double y,double dx, double dy){
        super(x,y,dx,dy);
        this.frontSpeed=25;
        this.rayon=15;//arbitraire pour l'instant
        this.nomObjet="MISSILE";
    }
    
    public void move(){
        x=x+dx*(frontSpeed*0.025);
        y=y+dy*(frontSpeed*0.025);
    }
    
    public void doCollision(Item item){
        item.frontSpeed=0;
        this.actif=false;
    }
    

    
    public void drawGraphTest(Graphics g){
        g.setColor(Color.green);
        g.fillOval((int)x,(int)(576-y),(int)rayon,(int)rayon);//rayon du cercle à adapter aver l'échelle
    }
    
}
