public class Bombe extends Projectile{//WORK IN PROGRESS il faut trouver comment d�tecter s'il y a des karts dans la zone d'explosion quand elle explose
    
    private static double rayExpl;//le rayon de l'explosion
    
    public Bombe(double x, double y,double dx, double dy){
        super(x,y,dx,dy);  
        rayExpl=3;
    }
    
    public boolean quandExploser(){
        boolean exp=false;
        if (tempsVie>160){//la bombe explose au bout de 4 secondes, on incr�mente le temps de vie toutes les 25ms --->160 it�rations en tout
            exp=true;
        }return exp;
    }
    public void doCollision(Item item){
        
    }
    
    public void avance(){
        
    }
    public void avance(int i){
        
    }
}
