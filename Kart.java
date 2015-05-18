import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Kart extends Item {
    
    private double rayCourb;
    private double maxAcc;
    private double poids;
    private double fCent;
    private double adherence;
    private double maxSpeed;
    private double h,l;//hauteur et largeur de l'objet, la hauteur �tant dans la direction du vecteur (dx,dy)
    private boolean derapeDroite;
    private boolean derapeGauche;
    private double contrebraque;// coeff de contrebraquage lors du d�rapage
    private double contrebraqueMax;// attention cela correspond � un coeff minimum
    private double coeff;//coeff qui fait diminuer la fadm lors du d�rapage
    private double coeffFrein;//coeff de freinage qui fait augmenter la fCent lorsqu'on freine;
    private double dxDir,dyDir;
    private int compt;//compteur d�rapage
    
    
    
    public Kart(double x,double y,double dx,double dy,double maxSpeed, double rayCourb, double maxAcc,double poids,double adherence,int numeroImage ){//pas encore ts les attributs
        super(x,y,dx,dy);
        h=2;//hauteur de 2m
        l=1;//largeur de 1m
        this.maxSpeed=maxSpeed;
        this.rayCourb=rayCourb;
        this.maxAcc=maxAcc;
        derapeDroite=false;
        derapeGauche=false;
        contrebraque=1;
        coeff=0.2;//anciennement 0.5
        contrebraqueMax=0.25;
        this.poids=poids;
        this.adherence=adherence;
        
        this.nomObjet="KART";
        
        frontSpeed=0;
        if (numeroImage==1){
            try{
                image= ImageIO.read(new File("Navire.png"));
            }catch (IOException e){
                System.out.println("Could not load image file");
                System.exit(1);
            }
        }
    }
    public void tourne(char a){
        double theta=0;
        if (a=='g'&& derapeGauche==false && derapeDroite==false && frontSpeed>0.5){
            System.out.println("tourne � gauche");
            double dxc=-dy;
            double dyc=dx;
            double xc=x+rayCourb*dxc;
            double yc=y+rayCourb*dyc;
            theta=Math.atan2((y-yc),(x-xc));
            double dtheta=frontSpeed*(0.025)/rayCourb;; //le 0.025 vient du timer � 25ms, c'est dt
            x=xc+rayCourb*Math.cos(theta+dtheta);
            y=yc+rayCourb*Math.sin(theta+dtheta);  
            double norme=Math.sqrt((x-xc)*(x-xc)+(y-yc)*(y-yc));
            dy=(x-xc)/norme;
            dx=-(y-yc)/norme;
        }
    
        if (a=='d' && derapeDroite==false && derapeGauche==false && frontSpeed>0.5){
            System.out.println("tourne � droite");
            double dxc=dy;
            double dyc=-dx;
            double xc=x+rayCourb*dxc;
            double yc=y+rayCourb*dyc;
            theta=Math.atan2((y-yc),(x-xc));
            double dtheta=frontSpeed*(0.025)/rayCourb; 
            x=xc+rayCourb*Math.cos(theta-dtheta);
            y=yc+rayCourb*Math.sin(theta-dtheta); 
            double norme=Math.sqrt((x-xc)*(x-xc)+(y-yc)*(y-yc));
            dy=-(x-xc)/norme;
            dx=(y-yc)/norme;
        };
    }
    
    public void avance(int i){// si i=0, le kart avance normalement, sinon il se contente d'augmenter la vitesse sans modifier la position
        if (derapeDroite || derapeGauche ){
           frontSpeed=frontSpeed-0.06; //Quand le kart d�rape il perd de la vitesse
        }
        else if (frontSpeed<maxSpeed-6){//le kart a plus de mal � acc�lerer � haute vitesse
            frontSpeed=frontSpeed+maxAcc; 
        }
        else{
            frontSpeed=frontSpeed+maxAcc-0.03;
            if (frontSpeed>maxSpeed){
                frontSpeed=maxSpeed;
            }
            
        }System.out.println(frontSpeed);
        if (i==0 && derapeDroite==false && derapeGauche==false){
            x=x+dx*(frontSpeed*0.025);
            y=y+dy*(frontSpeed*0.025);
        } 
    }
    
    public void ralentit(int i){
         if (frontSpeed>0.5){
            frontSpeed=frontSpeed-0.1;
        }
        else{
            frontSpeed=0;
        }
        if (i==0 && derapeDroite==false && derapeGauche==false){
            x=x+dx*(frontSpeed*0.025);
            y=y+dy*(frontSpeed*0.025);   
        }
    }
    
    public void freine(){
        if (derapeDroite || derapeGauche){//le kart perd de la vitesse moins vite quand �a d�rape
            frontSpeed=frontSpeed-0.9;
        }
        else{
            frontSpeed=frontSpeed-0.12;
        }
    }
    
    public void derapage(char tourne,char freine){ 
        double fAdm=adherence*1815;//limite de d�rapage � 11 m/s d'un kart avec un poids moyen (150kg) et une adh�rence neutre (1), Rc=10m
        if (derapeDroite==false && derapeGauche==false){//coef de contrebraquage neutre si le kart ne d�rape pas
            contrebraque=1;
        }                                       
        else if ((derapeGauche && tourne=='d')||(derapeDroite && tourne=='g')){//coef de contrebraquage
            if (contrebraque>contrebraqueMax){
                contrebraque=contrebraque-0.028;
                
                if (contrebraque<contrebraqueMax){
                    contrebraque=contrebraqueMax;
                }
            }System.out.println("contrebraque="+contrebraque);
        }
        else if ((derapeGauche || derapeDroite) && tourne=='0'){//coeff moins fort quand on ne fait que redresser les roues
            if (contrebraque>contrebraqueMax+0.4){//d'o� le 0.4
                contrebraque=contrebraque-0.0125;
                if (contrebraque<contrebraqueMax+0.4){
                    contrebraque=contrebraqueMax+0.4;
                }
            }  
        }

        if (freine=='y'){         //coeff de freinage qui augmente la facilit� � d�raper quand on freine
            coeffFrein=1.4;
        }
        else{
            coeffFrein=1;
        }
        fCent=coeffFrein*contrebraque*poids*frontSpeed*frontSpeed/(rayCourb/15);//calcul de la force centrifuge
        if (fCent<fAdm*coeff){
            derapeGauche=false;
            derapeDroite=false;
            coeff=0.2; //reset du coeff � 0.2 � la fin du d�rapage
            compt=0;//reset du compteur d�rapage
            
        }
        if ((tourne=='g' && fCent>fAdm && derapeDroite==false) || (derapeGauche && fCent>fAdm*coeff )){
            coeff=0.2;//reset du coeff 0.2
            compt++;
            if (compt>10){//le d�rapage ne s'active que si on a commenc� � tourner depuis un certain temps (250ms)
                if (derapeGauche==false){
                    dxDir=dx;//les directions vers lesquelles la voiture roulait quand elle a commenc� � d�raper
                    dyDir=dy;
                }
                derapeGauche=true;
                System.out.println("fadm="+fAdm+" fCent="+fCent);
                double normexDir=1;
                double normeyDir=1;
                double normex=1;
                double normey=1;
                System.out.println("d�rape gauche");
                double thetaDir=frontSpeed*frontSpeed*coeffFrein*0.0008;
                if (dyDir!=0){                                   //cette partie permet de calculer les nouveaux dxDir et dyDir de la voiture=direction vers laquelle la voiture roule         
                    normexDir=Math.tan(thetaDir)*(dyDir/Math.abs(dyDir));
                }else if (dxDir<0){
                    normexDir=-1;
                }
                if (dxDir!=0){
                    normeyDir=Math.tan(thetaDir)*(dxDir/Math.abs(dxDir));
                }else if (dyDir>0){
                    normexDir=-1;
                }
                double x2Dir=x+dxDir-Math.cos(thetaDir)*normexDir;
                double y2Dir=y+dyDir+Math.sin(thetaDir)*normeyDir;
                double normeDir=Math.sqrt((x2Dir-x)*(x2Dir-x)+(y2Dir-y)*(y2Dir-y));
                dxDir=(x2Dir-x)/normeDir;
                dyDir=(y2Dir-y)/normeDir;
                
                
                double thetad=frontSpeed*0.01*coeffFrein;// coeff arbitraire pour avoir un angle convenable de d�rapage (si on freine l'angle est plus grand) 
                System.out.println("THETA= "+thetad);          //le theta repr�sente l'angle de pivotement de la voiture sur elle m�me=/=thetaDir          
                if (dy!=0){                                   //cette partie permet de calculer les nouveaux dx et dy=orientation de la voiture        
                    normex=Math.tan(thetad)*(dy/Math.abs(dy));
                }else if (dx<0){
                    normex=-1;
                }
                if (dx!=0){
                    normey=Math.tan(thetad)*(dx/Math.abs(dx));
                }else if (dy>0){
                    normex=-1;
                }
                double x2=x+dx-Math.cos(thetad)*normex;
                double y2=y+dy+Math.sin(thetad)*normey;
                double norme=Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
                dx=(x2-x)/norme;
                dy=(y2-y)/norme;
                
                if (Math.abs(Math.atan2(dy,dx)-Math.atan2(dyDir,dxDir))>0.5){//il derape plus longtemps si la direction de la voiture est diff�rente de celle vers laquelle elle d�rape
                    coeff=0.1;
                    System.out.println("WOLOLOLO");
                }
            }
            
        }
        
        else if ((tourne=='d' && fCent>fAdm && derapeGauche==false)|| (derapeDroite && fCent>fAdm*coeff)){
            coeff=0.2;
            compt++;
            if (compt>10){
                if (derapeDroite==false){
                    dxDir=dx;//les directions vers lesquelles la voiture roulait quand elle a commenc� � d�raper
                    dyDir=dy;
                }
                derapeDroite=true;
                System.out.println("d�rape droite");
                double normexDir=1;
                double normeyDir=1;
                double normex=1;
                double normey=1;
                
                double thetaDir=frontSpeed*frontSpeed*coeffFrein*0.0008;
                if (dyDir!=0){                                           
                    normexDir=Math.tan(thetaDir)*(dyDir/Math.abs(dyDir));
                }else if (dxDir<0){
                    normexDir=-1;
                }
                if (dxDir!=0){
                    normeyDir=Math.tan(thetaDir)*(dxDir/Math.abs(dxDir));
                }else if (dyDir>0){
                    normexDir=-1;
                }
                double x2Dir=x+dxDir+Math.cos(thetaDir)*normexDir;
                double y2Dir=y+dyDir-Math.sin(thetaDir)*normeyDir;
                double normeDir=Math.sqrt((x2Dir-x)*(x2Dir-x)+(y2Dir-y)*(y2Dir-y));
                dxDir=(x2Dir-x)/normeDir;
                dyDir=(y2Dir-y)/normeDir;
                
                double thetad=frontSpeed*0.01*coeffFrein;
                System.out.println("THETA= "+thetad);
                if (dy!=0){
                    normex=Math.tan(thetad)*(dy/Math.abs(dy));
                }else if (dx<0){
                    normex=-1;
                }
                if (dx!=0){
                    normey=Math.tan(thetad)*(dx/Math.abs(dx));
                }else if (dy>0){
                    normex=-1;
                }
                double x2=x+dx+Math.cos(thetad)*normex;
                double y2=y+dy-Math.sin(thetad)*normey;
                double norme=Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
                dx=(x2-x)/norme;
                dy=(y2-y)/norme;
                System.out.println("norme="+norme);
                System.out.println("dx="+dx);
                System.out.println("dy="+dy);
            
                if (Math.abs(Math.atan2(dy,dx)-Math.atan2(dyDir,dxDir))>0.5){
                    coeff=0.1;
                    System.out.println("WOLOLOLO");
                }
            }
        }
        if (derapeDroite || derapeGauche){
            x=x+dxDir*(frontSpeed*0.025);
            y=y+dyDir*(frontSpeed*0.025);
        }
        //System.out.println("coeff="+coeff);
    }
    
    public void drawGraphTest(Graphics g){
        
        g.drawImage(image,(int)(x),(int)(576-y),null);
        g.setColor(Color.red);
        g.drawLine((int)x,(int)(576-y),(int)(x+dx*20),(int)((576-(y+dy*20))));//�a affiche une ligne qui indique la direction de la voiture pour m'aider lors des tests
    }
    
    public void draw(Graphics g, int ax, int ay){
            
            g.drawImage(image,ax,ay,null);
            g.setColor(Color.red);
            g.drawLine(ax,ay,(int)(ax+dx*20),(int)(ay-dy*20));//�a affiche une ligne qui indique la direction de la voiture pour m'aider lors des tests
        }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    
    public boolean collision(Item item){// les coordonn�es des coins du kart doivent avoir �t� mises � jour 
        this.calculTheta();                                                                       
        item.calculTheta();
        boolean colli=false;                                                                          
        //test pr�alable pour voir si les objets sont proches ou pas                                  
        if (Math.sqrt((item.x-this.x)*(item.x-this.x)-(item.y-this.y)*(item.y*this.y))>4){      
            return colli;                                                                          
        }                                                                                  
                               
        else if (item.nomObjet=="KART"){   
            //calcul des �quations des 4 droites du kart
            double a1=-1/Math.tan(item.theta);//a1=a3
            double b1=item.y-(h/2)*Math.sin(item.theta)-a1*(item.x-(h/2)*Math.cos(item.theta));
            double a2=Math.tan(item.theta);//a4=a2
            double b2=item.y+(l/2)*Math.cos(item.theta)-a2*(item.x-(l/2)*Math.sin(item.theta));
            double b3=item.y+(h/2)*Math.sin(item.theta)-a1*(item.x+(h/2)*Math.cos(item.theta));
            double b4=item.y-(l/2)*Math.cos(item.theta)-a2*(item.x+(l/2)*Math.sin(item.theta));
            
            for (int i=0;i<5;i++){ //on teste si un des points du kart v�rifie l'�quation pour �tre � l'int�rieur de l'autre  
                if (a1*tabx[i]-taby[i]+b1<0 && a2*tabx[i]-taby[i]+b2>0 && a1*tabx[i]-taby[i]+b3>0 && a2*tabx[i]-taby[i]+b4<0){
                    colli=true;
                    return colli;
                }
            }
        }return colli;
    }
    
    public void coordCoinsX(){// ici on calcule les coordonn�s des coins du kart cf sch�ma 
        //Attention theta doit �tre mis � jour                                                               
        this.tabx[0]=this.x;//au milieu du kart                                                               
        this.tabx[1]=this.x-(h/2)*Math.cos(this.theta)+(l/2)*Math.sin(this.theta);//en bas � droite du Kart          
        this.tabx[2]=this.x-(h/2)*Math.cos(this.theta)-(l/2)*Math.sin(this.theta);//en bas � gauche          
        this.tabx[3]=this.x-(l/2)*Math.sin(this.theta)+(h/2)*Math.cos(this.theta);//en haut � gauche          
        this.tabx[4]=this.x+(h/2)*Math.cos(this.theta)+(l/2)*Math.sin(this.theta);//en haut � droite
    }
    
    public void coordCoinsY(){//Attention theta doit �tre mis � jour 
 
        this.taby[0]=this.y;                                                                
        this.taby[1]=this.y-(h/2)*Math.sin(this.theta)-(l/2)*Math.cos(this.theta);                    
        this.taby[2]=this.y-(h/2)*Math.sin(this.theta)+(l/2)*Math.cos(this.theta);                   
        this.taby[3]=this.y+(l/2)*Math.cos(this.theta)+(h/2)*Math.sin(this.theta);                    
        this.taby[4]=this.y+(h/2)*Math.sin(this.theta)-(l/2)*Math.cos(this.theta);
    }
    
    public boolean colliMurs(){
        boolean colliMur=false;
        this.coordCoinsX();
        this.coordCoinsY();
        for (int i=0;i<5;i++){
            //regarde si un des coins est dans l'ellipse int�rieur ou en dehors de l'ellipse ext�rieur
            if ((tabx[i]*tabx[i]/((PanelField.aInt)*(PanelField.aInt)))+(taby[i]*taby[i]/((PanelField.bInt)*(PanelField.bInt)))<=1 || (tabx[i]*tabx[i]/((PanelField.aExt)*(PanelField.aExt)))+(taby[i]*taby[i]/((PanelField.bExt)*(PanelField.bExt)))>=1 ){
                colliMur=true;
                return colliMur;
            }
            
        }return colliMur;
    }
    
    public void doCollision(Item item){
        
    }
    
    public void setSpeed(double a){
        frontSpeed=a;
    }
    
    public void setMaxSpeed(double a){
        maxSpeed=a;
    }
    public double getSpeed(){
        return frontSpeed;
    }
    
    public double getMaxSpeed(){
        return maxSpeed;
    }
    
    public void avance(){//m�thode qui ne sert � rien ici car pour le kart on utilisera toujours celle avec int i en param�tre
        
    }
}
