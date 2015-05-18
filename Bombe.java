public class Bombe extends Projectile{//WORK IN PROGRESS il faut trouver comment détecter s'il y a des karts dans la zone d'explosion quand elle explose
    
    private static double rayExpl;//le rayon de l'explosion
    
    public Bombe(double x, double y,double dx, double dy){
        super(x,y,dx,dy);  
        rayExpl=3;
    }
    
    public boolean quandExploser(){
        boolean exp=false;
        if (tempsVie>160){//la bombe explose au bout de 4 secondes, on incrémente le temps de vie toutes les 25ms --->160 itérations en tout
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
