public  abstract class Projectile extends Item{
    protected double rayon;// le rayon du missile qui est en fait une boule tous les projectiles ont le même pour plus de simplicité
    
    public Projectile(double x, double y, double dx, double dy){
        super(x,y,dx,dy);
        this.rayon=0.75;
    }
    
    public boolean collision(Item item){
        boolean colli=false;
        if (item.nomObjet=="KART"){
            for (int i=0;i<5;i++){
                if (Math.sqrt((this.x-item.tabx[i])*(this.x-item.tabx[i])+(this.y-item.taby[i])*(this.y-item.taby[i]))<this.rayon){
                    colli=true;
                }
            }
            
        }
        if (item.numObjet!=6){//on a donc affaire à un bonus
            if (Math.sqrt((this.x-item.x)*(this.x-item.x)+(this.y-y)*(this.y-item.y))<this.rayon){
                colli=true;
            }
        }
        
        return colli;
    } 
    
    public boolean colliMurs(){
        boolean colliMur=false;
        if ((x*x/((PanelField.aInt)*(PanelField.aInt)))+(y*y/((PanelField.bInt)*(PanelField.bInt)))<=1 || (x*x/((PanelField.aExt)*(PanelField.aExt)))+(y*y/((PanelField.bExt)*(PanelField.bExt)))>=1 ){
            colliMur=true;
        }return colliMur;
    }
        
    
    
    
}



