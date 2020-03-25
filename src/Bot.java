import javax.rmi.ssl.SslRMIClientSocketFactory;

public class Bot {
    public Bot(Plateau P){
        this.P = P;
        this.valeurs = new int[P.n][P.m];
        System.out.println("Bot initiated");
    }

    public void play(){
        System.out.println("Bot is playing");
        double column = P.m*Math.random();
        // Bot is thinking
        for(int i = 0;i<P.n;i++){
            //System.out.print("|");
            for(int j = 0;j<P.m;j++){
                valeurs[i][j] = valeur(i,j);
                //System.out.print(valeurs[i][j]+"|");
            }
            //System.out.println();
        }
        System.out.println();
        for(int i=0;i<P.n;i++){
            System.out.print("|");
            for(int j=0;j<P.m;j++){
                System.out.print(valeurs[i][j]+"|");
            }
            System.out.println();
        }
        int xmem = -1;
        int ymem = -1;
        int maxval = 0;
        for(int k = 0;k<P.m;k++){
            if(maxval<=valeurs[available(k)][k]){
                xmem = k;               // LARGEUR
                ymem = available(k);    // HAUTEUR
                maxval = valeurs[available(k)][k];
            }
        }
        System.out.println();
        // Bot has decided
        try {
            P.joue(xmem);
        }
        catch(ErrGagne e){
            System.out.println("Bot has not won");
        }
        System.out.println("Bot played");
    }

    public int valeur(int j,int i){
        int k;
        int maxRed = 0;
        int maxYellow = 0;
        //direction 1
        int cR = 0;
        int cY = 0;
        for(k=-3;k<=3;k++){
            cR = P.compte(i+k,j,cR,1); maxRed = Math.max(maxRed,cR);
            cY = P.compte(i+k,j,cY,2); maxYellow = Math.max(maxYellow,cY);
            //System.out.println("("+cR+","+cY+")");
            /*if(cR==4 && cY==4)
                break;*/
        }
        //direction 2
        cR = 0;
        cY = 0;
        for(k=-3;k<=3;k++){
            cR = P.compte(i,j+k,cR,1); maxRed = Math.max(maxRed,cR);
            cY = P.compte(i,j+k,cY,2); maxYellow = Math.max(maxYellow,cY);
            /*if(cR==4 && cY==4)
                break;*/
        }
        //direction 3
        cR = 0;
        cY = 0;
        for(k=-3;k<=3;k++){
            cR = P.compte(i+k,j+k,cR,1); maxRed = Math.max(maxRed,cR);
            cY = P.compte(i+k,j+k,cY,2); maxYellow = Math.max(maxYellow,cY);
            /*if(cR==4 && cY==4)
                break;*/
        }
        //direction 4
        cR = 0;
        cY = 0;
        for(k=-3;k<=3;k++){
            cR = P.compte(i+k,j-k,cR,1); maxRed = Math.max(maxRed,cR);
            cY = P.compte(i+k,j-k,cY,2); maxYellow = Math.max(maxYellow,cY);
            /*if(cR==4 && cY==4)
                break;*/
        }
        return maxRed+maxYellow;
    }

    public int available(int j){
        int iter = P.n-1;
        while(iter>=0 && P.tab[iter][j].getColor()!=0){
            iter-=1;
        }
        //System.out.print("["+iter+"]["+j+"]");
        return iter;
    }

    int valeurs[][];
    Plateau P;
}
