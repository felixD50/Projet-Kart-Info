public class Missile extends Projectile {
    
    public Missile(double x, double y,double dx, double dy){
        super(x,y,dx,dy);
        this.frontSpeed=25;
    }
    
    public void avance(){
        x=x+dx*(frontSpeed*0.025);
        y=y+dy*(frontSpeed*0.025);
    }
    
    public void doCollision(Item item){
        item.frontSpeed=0;
        this.actif=false;
    }
    
    public void avance(int i){
        
    }
    
}
