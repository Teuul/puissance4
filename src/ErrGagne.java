import java.lang.management.PlatformLoggingMXBean;

public class ErrGagne extends Exception {
    public ErrGagne(int code,Plateau P){
        this.code = code;
        this.P = P;
        //System.out.println("Joueur " + tour + " n'a pas gagné.");
        if(code ==3){
            System.out.println("The grid is full. Draw !");
            P.restartButton.newGame();

        }
    }

    int code;
    Plateau P;
}
