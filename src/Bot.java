public class Bot{
    public Bot(Plateau P){
        this.P = P;
        this.valeurs = new int[P.n][P.m];
    }

    public void play(){ // remplacer par un thread
        //int column = chooseRandom(P.m,P.n,P.tab);
        int[][] grid = Case.colorGrid(P.n,P.m,P.tab);
        //int column = chooseGreedy(P.n,P.m,P.tab);
        int column = chooseMinmax(P.n,P.m,grid);
        //int column = chooseAlphaBeta(P.n,P.m,grid);

        try {
            P.joue(column);
        }
        catch(ErrGagne e){
            //System.out.println("Bot has not won");
        }
    }

    public int valeur(int j,int i,int n,int m,int[][] tab){
        int k;
        int maxRed = 0;
        int maxYellow = 0;
        //direction 1
        int cR = 0;
        int cY = 0;
        for(k=-3;k<=3;k++){
            cR = compte(i+k,j,cR,1,n,m,tab); maxRed = Math.max(maxRed,cR);
            cY = compte(i+k,j,cY,2,n,m,tab); maxYellow = Math.max(maxYellow,cY);
            //System.out.println("("+cR+","+cY+")");
        }
        //direction 2
        cR = 0;
        cY = 0;
        for(k=-3;k<=3;k++){
            cR = compte(i,j+k,cR,1,n,m,tab); maxRed = Math.max(maxRed,cR);
            cY = compte(i,j+k,cY,2,n,m,tab); maxYellow = Math.max(maxYellow,cY);
        }
        //direction 3
        cR = 0;
        cY = 0;
        for(k=-3;k<=3;k++){
            cR = compte(i+k,j+k,cR,1,n,m,tab); maxRed = Math.max(maxRed,cR);
            cY = compte(i+k,j+k,cY,2,n,m,tab); maxYellow = Math.max(maxYellow,cY);
        }
        //direction 4
        cR = 0;
        cY = 0;
        for(k=-3;k<=3;k++){
            cR = compte(i+k,j-k,cR,1,n,m,tab); maxRed = Math.max(maxRed,cR);
            cY = compte(i+k,j-k,cY,2,n,m,tab); maxYellow = Math.max(maxYellow,cY);
        }
        return maxYellow-maxRed;
        //return Math.max(maxYellow,maxRed);
    }

    public int chooseRandom(int m,int n,Case[][] grid){
        int res = (int)(m*Math.random());
        while(available(res,n,grid)==-1)
            res = (int)(m*Math.random());
        return res;
    }

    public int chooseGreedy(int n,int m,Case[][] tab){ // GREEDY ALGORITHM
        int[][] grid = Case.colorGrid(n,m,tab);
        valeurs = gridValues(n,m,grid);
        printGrid(n,m,valeurs);

        int[] xmem = new int [m];
        int lenMem = 0;
        int maxval = 0;
        for(int k = 0;k<m;k++){
            if(available(k,n,tab)!=-1) {
                if (maxval < valeurs[available(k, n, tab)][k]) {
                    xmem = new int[m];
                    xmem[0] = k;
                    lenMem = 1;
                    maxval = valeurs[available(k, n, tab)][k];
                }
                else if(maxval == valeurs[available(k, n, tab)][k]){
                    xmem[lenMem] = k;
                    lenMem++;
                }
            }
        }
        return xmem[(int)(lenMem*Math.random())];
    }

    public int chooseMinmax(int n,int m,int[][] grid){
        double bestScore = -100000;
        double score;
        int depth = 7; // up to the user
        //int kbest = 0;
        int[] kbest = new int[m];
        int lenKbest = 0;
        int i,j;
        for(int k=0;k<m;k++){
            j = k;
            i = available(k,n,grid);
            if(i!=-1) {
                grid[i][j] = 2;
                int[][] newGrid = Case.copy(n,m,grid);
                score = scoreMinmax(n,m,newGrid,depth-1,1);
                System.out.println("["+i+","+j+"] | Depth:"+ depth +" | Mean:" + score);
                if (bestScore< score){
                    kbest = new int[m];
                    kbest[0] = k;
                    lenKbest = 1;
                    bestScore = score;
                }
                else if(bestScore == score){
                    kbest[lenKbest] = k;
                    lenKbest++;
                }
                grid[i][j] = 0;
            }
        }
        return kbest[(int)(lenKbest*Math.random())];
    }

    public double scoreMinmax(int n,int m,int[][] grid,int depth,int joueur){
        if(depth == 0 || gridIsfull(n,m,grid)){
            int[][]values = gridValues(n,m,grid);
            return gridMean(n,m,values);
        }
        else if(joueur == 2){       // maximizing --> bot
            double maxMean=-100000,mean;
            int kmax = 0;
            int i,j;
            for(int k=0;k<m;k++){
                j = k;
                i = available(k,n,grid);
                if(i!=-1) {
                    grid[i][j] = 2;
                    int [][] newGrid = Case.copy(n,m,grid);
                    //printGrid(n,m,grid);
                    mean = scoreMinmax(n,m,newGrid,depth-1,1);
                    if (maxMean < mean){
                        maxMean = mean;
                    }
                    grid[i][j] = 0;
                }
            }
            return maxMean;
        }
        else{                       // minimizing --> player
            double minMean = 100000,mean;
            int kmin = 0;
            int i,j;
            for(int k=0;k<m;k++){
                j = k;
                i = available(j,n,grid);
                if(i!=-1) {
                    grid[i][j] = 1;
                    int[][] newGrid = Case.copy(n,m,grid);
                    //printGrid(n,m,newGrid);
                    mean = scoreMinmax(n,m,newGrid,depth-1,2);
                    if (minMean > mean){
                        minMean = mean;
                    }
                    grid[i][j] = 0;
                }
            }
            return minMean;
        }
    }

    public int chooseAlphaBeta(int n,int m,int[][] grid){
        double bestScore = -100000;
        double score;
        int depth = 7;
        int[] kbest = new int[m];
        int lenKbest = 0;
        int i,j;
        double alpha,beta;
        for(int k=0;k<m;k++){
            j = k;
            i = available(k,n,grid);
            if(i!=-1) {
                grid[i][j] = 2;
                int[][] newGrid = Case.copy(n,m,grid);
                alpha = -100000;
                beta = 100000;
                score = scoreAlphaBeta(n,m,newGrid,depth-1,1,alpha,beta);
                System.out.println("["+i+","+j+"] | Depth:"+ depth +" | Mean:" + score);
                System.out.println("A: "+ alpha +" |B: "+ beta);
                if (bestScore< score){
                    kbest = new int[m];
                    kbest[0] = k;
                    lenKbest = 1;
                    bestScore = score;
                }
                else if(bestScore == score){
                    kbest[lenKbest] = k;
                    lenKbest++;
                }
                grid[i][j] = 0;
            }
        }
        return kbest[(int)(lenKbest*Math.random())];
    }

    public double scoreAlphaBeta(int n,int m,int[][] grid,int depth,int joueur,double alpha,double beta){
        if(depth == 0 || gridIsfull(n,m,grid)){
            int[][]values = gridValues(n,m,grid);
            return gridMean(n,m,values);
        }
        else if(joueur == 2){       // maximizing --> bot
            double maxMean=-100000,mean;
            int i,j;
            for(int k=0;k<m;k++){
                j = k;
                i = available(k,n,grid);
                if(i!=-1) {
                    grid[i][j] = 2;
                    int [][] newGrid = Case.copy(n,m,grid);
                    //printGrid(n,m,grid);
                    mean = scoreAlphaBeta(n,m,newGrid,depth-1,1,alpha,beta);
                    if (maxMean < mean){
                        maxMean = mean;
                    }
                    alpha = Math.max(alpha,mean);
                    if(alpha>=beta)
                        break;
                    grid[i][j] = 0;
                }
            }
            return maxMean;
        }
        else{                       // minimizing --> player
            double minMean = 100000,mean;
            int i,j;
            for(int k=0;k<m;k++){
                j = k;
                i = available(j,n,grid);
                if(i!=-1) {
                    grid[i][j] = 2; // grid[i][j] = 1; ???
                    int[][] newGrid = Case.copy(n,m,grid);
                    //printGrid(n,m,newGrid);
                    mean = scoreAlphaBeta(n,m,newGrid,depth-1,2,alpha,beta);
                    if (minMean > mean){
                        minMean = mean;
                    }
                    beta = Math.min(beta,mean);
                    if(alpha>=beta)
                        break;
                    grid[i][j] = 0;
                }
            }
            return minMean;
        }
    }

    public int available(int j,int n,Case[][] grid){
        int iter = n-1;
        while(iter>=0 && grid[iter][j].getColor()!=0){
            iter-=1;
        }
        return iter;
    }

    public int available(int j,int n,int[][] grid){
        int iter = n-1;
        while(iter>=0 && grid[iter][j]!=0){
            iter-=1;
        }
        return iter;
    }

    public int[][] gridValues(int n,int m,int[][] grid){
        int [][] res = new int[n][m];
        for(int i = 0;i<P.n;i++){
            for(int j = 0;j<P.m;j++){
                res[i][j] = valeur(i,j,n,m,grid);
            }
        }
        return res;
    }

    public double gridMean(int n,int m,int[][] tab){    // modified "mean"
        double sum = 0;
        double nb = 0;
        //System.out.println("#### CALCUL MOYENNE ####");
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(tab[i][j]!=0) {
                    sum += tab[i][j];
                    nb++;
                }
                //System.out.println("S="+sum);
            }
        }
        return sum/nb;
    }

    int compte(int x,int y,int c,int joueur,int n,int m,int[][] tab){
        if (getCellColor(x,y,n,m,tab) == joueur)
            return c + 1;
        return 0;
    }

    int getCellColor(int x,int y,int n,int m,int[][] tab){
        if(0<=x && x<m){
            if(0<=y && y<n){
                return tab[y][x];
            }
        }
        return -1;
    }

    public void printGrid(int n,int m,int[][] grid){
        for(int i=0;i<n;i++){
            System.out.print("|");
            for(int j= 0;j<m;j++){
                System.out.print(grid[i][j]+"|");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean gridIsfull(int n,int m,int[][] grid){
        int sum=0;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j]!=0)
                    sum++;
            }
        }
        return sum==n*m;
    }



    int valeurs[][];
    Plateau P;
}
