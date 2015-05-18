public class Banane extends Projectile{
    
    public Banane(double x, double y,double dx, double dy){
        super(x,y,dx,dy);    
    }
    
    public void doCollision(Item item){
        item.frontSpeed=0;
        this.actif=false;
    }
    
    public void avance(){
        
    }
    public void avance(int i){
        
    }
}
