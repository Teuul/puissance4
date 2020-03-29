public class Descente extends Thread{
    public Descente(int i,int j,Case[][] grille,int h,int tour){
        this.i = i;
        this.j = j;
        this.grille = grille;
        this.h = h;
        this.tour = tour;
    }
    public void run() {
        int iter = 0;
        while(iter!=i){
            // descente du jeton
            grille[iter][j].setColorDescente(tour);
            if(iter!=0)
                grille[iter-1][j].setColorDescente(0); // remettre à gris celle d'avant
            try {
                sleep(80);
                iter++;
            } catch (InterruptedException e) {

            }
        }
        if(iter!=0)
            grille[iter-1][j].setColorDescente(0); // remettre à gris celle d'avant
        grille[iter][j].setColor(tour);
        interrupt();
    }

    int i,j,h,tour;
    Case[][] grille;
}
